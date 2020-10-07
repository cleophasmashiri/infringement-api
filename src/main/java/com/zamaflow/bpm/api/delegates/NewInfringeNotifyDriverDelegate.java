package com.zamaflow.bpm.api.delegates;

import com.zamaflow.bpm.api.domain.Driver;
import com.zamaflow.bpm.api.domain.Infringement;
import com.zamaflow.bpm.api.domain.Notification;
import com.zamaflow.bpm.api.domain.enumeration.InfringementActionType;
import com.zamaflow.bpm.api.service.EmailDispatcher;
import com.zamaflow.bpm.api.service.InfringementService;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class NewInfringeNotifyDriverDelegate implements JavaDelegate {

    private final Logger LOGGER = LoggerFactory.getLogger(NewInfringeNotifyDriverDelegate.class);

    @Autowired
    private InfringementService infringementService;

    @Autowired
    private EmailDispatcher emailDispatcher;

    @Value("${bpm.tasks.baseurl}")
    public String taskUrl;

    @Value("${bpm.tasks.fromemail}")
    public String fromEmail;

    @Value("${sms.baseSmtpToSmsUrl}")
    private String baseSmtpToSmsUrl;

    @Value("${sms.smtpToSmsPassword}")
    private String smtpToSmsPassword;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        LOGGER.info("Sending Infringement Notification for Driver");
        String driverIdNumber = delegateExecution.getVariable("driverIdNumber").toString();
        Driver driver = infringementService.findDriverByNationalIdNumber(driverIdNumber);
        Infringement infringement =  infringementService.getInfringmentByprocessInstanceId(delegateExecution.getProcessInstanceId());
        infringementService.creatInfringementAction(infringement, delegateExecution.getProcessInstanceId(), delegateExecution.getVariable("infringementNotes").toString(), InfringementActionType.INFRINGEMENT_NOTIFICATION_SENT);
        
        emailDispatcher.send(new Notification()
        .setSubject("New Infringement Notification")
        .setToFrom(fromEmail)
        .setToEmail(driver.getEmail())
        .setBody("A new infringement created." + infringement.getInfringementType())
        .setAction(taskUrl)
        .setActionDescription("View Online"));

        // sms.getCellPhoneNumber() + baseSmtpToSmsUrl
        emailDispatcher.send(new Notification()
        .setSubject(smtpToSmsPassword)
        .setToFrom(fromEmail)
        .setToEmail(driver.getCellNumber() + "@" + baseSmtpToSmsUrl)
        .setBody("A new infringement created." + infringement.getInfringementType())
        .setAction(taskUrl)
        .setActionDescription("View Online"));


       
    }
}
