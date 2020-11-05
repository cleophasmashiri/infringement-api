package com.zamaflow.bpm.api.delegates;

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
public class RemindAdminDelegate implements JavaDelegate {

    private final Logger LOGGER = LoggerFactory.getLogger(RemindAdminDelegate.class);
    
    @Autowired
    private EmailDispatcher emailDispatcher;

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
        Infringement infringement =  infringementService.getInfringmentByprocessInstanceId(delegateExecution.getProcessInstanceId());
        infringementService.creatInfringementAction(infringement, delegateExecution.getProcessInstanceId(), delegateExecution.getVariable("infringementNotes").toString(), InfringementActionType.INFRINGEMENT_ADMIN_REMINDER_NOTIFICATION_SENT, 0.0, 0);
        emailDispatcher.send(new Notification()
        .setSubject("Infringement Review Task Reminder")
        .setToFrom(fromEmail)
        .setToEmail(adminEmail)
        .setBody("An review infringement task waiting for your action." + infringement.getInfringementType())
        .setAction(taskUrl)
        .setActionDescription("View Online")
        );
    }
}
