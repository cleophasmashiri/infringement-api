package com.zamaflow.bpm.api.delegates;

import com.zamaflow.bpm.api.domain.Infringement;
import com.zamaflow.bpm.api.service.InfringementService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class NewInfrigmentDelegate implements JavaDelegate {

    private final Logger LOGGER = LoggerFactory.getLogger(NewInfrigmentDelegate.class);
    
    @Autowired
    private InfringementService infringementService;

    @Value("${bpm.tasks.baseurl}")
    public String taskUrl;

    @Value("${bpm.tasks.fromemail}")
    public String fromEmail;

    @Value("${bpm.tasks.adminemail}")
    public String adminEmail;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        LOGGER.info("Sending Review Infringement Reminder to Admin");
        String plateNumber = delegateExecution.getVariable("plateNumber").toString();
        String nationalIdNumber = delegateExecution.getVariable("driverIdNumber").toString();
        String infrigementType = delegateExecution.getVariable("infringementType").toString();
        String infringementNotes = delegateExecution.getVariable("infringementNotes").toString();
        Infringement infringement = infringementService.createInfringement(delegateExecution.getProcessInstanceId(), plateNumber, nationalIdNumber, infrigementType, infringementNotes);
        // saveImage(delegateExecution, infringement, "image1");
        // saveImage(delegateExecution, infringement, "image2");
        // saveImage(delegateExecution, infringement, "image3");
    }

    private void saveImage(DelegateExecution delegateExecution, Infringement infringement, String image) {
        // if (delegateExecution.getVariable(image) != null) {
        //     Document document = new Document(image, delegateExecution.getVariable(image).toString(), infringement);
        //     documentRepository.save(document);
        // }
    }
}
