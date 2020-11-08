package com.zamaflow.bpm.api.delegates;

import com.zamaflow.bpm.api.domain.Driver;
import com.zamaflow.bpm.api.domain.Infringement;
import com.zamaflow.bpm.api.domain.InfringementAction;
import com.zamaflow.bpm.api.domain.enumeration.InfringementActionType;
import com.zamaflow.bpm.api.service.EmailDispatcher;
import com.zamaflow.bpm.api.service.InfringementService;
import com.zamaflow.bpm.api.service.InvoiceService;
import com.zamaflow.bpm.api.service.impl.PdfGeneratorUtil;

import liquibase.pro.packaged.i;
import liquibase.pro.packaged.iF;

import com.zamaflow.bpm.api.domain.InvoiceBase;
import com.zamaflow.bpm.api.domain.LineItem;
import com.zamaflow.bpm.api.domain.Notification;
import com.zamaflow.bpm.api.domain.Quotation;

import org.camunda.bpm.engine.variable.value.FileValue;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ProcessAdminActionDelegate implements JavaDelegate {

	private final Logger LOGGER = LoggerFactory.getLogger(ProcessAdminActionDelegate.class);

	@Autowired
	private InfringementService infringementService;

	@Autowired
	private PdfGeneratorUtil pdfGenaratorUtil;

	@Autowired
	private InvoiceService quotationService;

	@Autowired
	private EmailDispatcher emailDispatcher;

	@Value("${bpm.tasks.fromemail}")
	public String fromEmail;

	@Value("${bpm.tasks.baseurl}")
	public String taskUrl;

	@Override
	public void execute(DelegateExecution delegateExecution) throws Exception {
		LOGGER.info("ProcessAdminAction ..");
		Infringement infringement = infringementService
				.getInfringmentByprocessInstanceId(delegateExecution.getProcessInstanceId());
		String adminSelects = delegateExecution.getVariable("trafficAdminSelects") != null
				? delegateExecution.getVariable("trafficAdminSelects").toString()
				: "";
				String subject = "";
		InfringementActionType infringementActionType = null;
		switch (adminSelects) {
		case "Create Courtesy Letter":
			infringementActionType = InfringementActionType.INFRINGEMENT_CREATE_COURTESY_LETTER;
			subject = "Courtesy Letter";
			break;
		case "Create Enforcement Order":
			infringementActionType = InfringementActionType.INFRINGEMENT_CREATE_INFORCEMENT_ORDER;
			subject = "Enforcement Order";
			break;
		case "Create Warrant of Execution":
			subject = "Warrant of Execution";
			infringementActionType = InfringementActionType.INFRINGEMENT_WARRANT_ORDER;
			break;
		}
		if (infringementActionType != null) {
			String driverIdNumber = delegateExecution.getVariable("driverIdNumber").toString();
			Driver driver = infringementService.findDriverByNationalIdNumber(driverIdNumber);
			List<LineItem> fineLineItems = new ArrayList();
			List<LineItem> demeritLineItems = new ArrayList();
			List<Infringement> infringements = this.infringementService.findByDriverEmail(driver.getEmail());
			if (infringements != null && infringements.size() > 0) {
				for (Infringement i : infringements) {
					for (InfringementAction a : i.getInfringementActions()) {
						if (a.getInfringementActionType() == InfringementActionType.INFRINGEMENT_CREATE_FINE || a
								.getInfringementActionType() == InfringementActionType.INFRINGEMENT_PAY_FINE
								| a.getInfringementActionType() == InfringementActionType.INFRINGEMENT_PAYMENT_DONE)
							fineLineItems.add(new LineItem(
									"Infringement: " + i.getProcessInstanceId() + " " + a.getInfringementActionType(),
									1, a.getAmount()));
						if (a.getInfringementActionType() == InfringementActionType.INFRINGEMENT_CANCEL_DEMERIT || a
								.getInfringementActionType() == InfringementActionType.INFRINGEMENT_CREATE_DEMERIT)
							demeritLineItems.add(new LineItem(
									"Infringement: " + i.getProcessInstanceId() + " " + a.getInfringementActionType(),
									1, a.getPoints()));
					}
				}
			}
			InvoiceBase fines = new Quotation().addressline1(driver.getProvince()).addressCity(driver.getCity())
					.customerName(driver.getFirstName() + " " + driver.getLastName()).lineItems(fineLineItems);
			InvoiceBase demerits = new Quotation().addressline1(driver.getProvince()).addressCity(driver.getCity())
					.customerName(driver.getFirstName() + " " + driver.getLastName()).lineItems(demeritLineItems);
			
			File finesFile = pdfGenaratorUtil.createPdf("quotation", fines, delegateExecution,
					subject);
			File demeritFile = pdfGenaratorUtil.createPdf("quotation", demerits, delegateExecution,
					"Demerits");
			emailDispatcher
					.send(new Notification()
							.setSubject(subject)
							.setToFrom(fromEmail).
							setToEmail(driver.getEmail())
							.setBody("A new " + subject + " created." + infringement.getInfringementType() )
							.setAction(taskUrl).setActionDescription("View Online")
							.setAttachment(finesFile)
							.setAttachment(demeritFile));

			infringementService.creatInfringementAction(infringement, delegateExecution.getProcessInstanceId(),
					delegateExecution.getVariable("adminNotes").toString(), infringementActionType, 0.0, 0);

		}

	}
}
