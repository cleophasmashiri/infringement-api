package com.zamaflow.bpm.api.delegates;

import com.zamaflow.bpm.api.domain.Driver;
import com.zamaflow.bpm.api.domain.Infringement;
import com.zamaflow.bpm.api.domain.Notification;
import com.zamaflow.bpm.api.domain.enumeration.InfringementActionType;
import com.zamaflow.bpm.api.service.EmailDispatcher;
import com.zamaflow.bpm.api.service.InfringementService;

import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AssignDriverTaskListener implements TaskListener {

    private final Logger LOGGER = LoggerFactory.getLogger(AssignAnotherDriverDelegate.class);

    @Autowired
    private EmailDispatcher emailDispatcher;

    @Autowired
    private InfringementService infringementService;

    @Value("${bpm.tasks.baseurl}")
    public String taskUrl;

    @Value("${bpm.tasks.fromemail}")
    public String fromEmail;

    @Override
    public void notify(DelegateTask delegateTask) {
        LOGGER.info("Assign Driver");
        String driverIdNumber = delegateTask.getVariable("driverIdNumber").toString();
        Driver driver = infringementService.findDriverByNationalIdNumber(driverIdNumber);
        delegateTask.setAssignee(driver.getEmail());
        Infringement infringement = infringementService
                .getInfringmentByprocessInstanceId(delegateTask.getProcessInstanceId());
        infringementService.creatInfringementAction(infringement, delegateTask.getProcessInstanceId(),
        delegateTask.getVariable("driverNotes").toString(),
                InfringementActionType.INFRINGEMENT_ASSIGN_DRIVER);

                emailDispatcher.send(new Notification()
                .setSubject("New Infringement Notification")
                .setToFrom(fromEmail)
                .setToEmail(driver.getEmail())
                .setBody("A new infringement created." + infringement.getInfringementType())
                .setAction(taskUrl)
                .setActionDescription("View Online"));
    

    }
}
