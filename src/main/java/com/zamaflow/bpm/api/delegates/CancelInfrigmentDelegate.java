package com.zamaflow.bpm.api.delegates;

import com.zamaflow.bpm.api.domain.Infringement;
import com.zamaflow.bpm.api.domain.enumeration.InfringementActionType;
import com.zamaflow.bpm.api.service.InfringementService;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CancelInfrigmentDelegate implements JavaDelegate {

    private final Logger LOGGER = LoggerFactory.getLogger(CancelInfrigmentDelegate.class);

    @Autowired
    private InfringementService infringmentService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        delegateExecution.setVariable("CancelInfrigmentDelegate", Boolean.TRUE);
        LOGGER.info("Cancel Infrigment");
        Infringement infringement =  infringmentService.getInfringmentByprocessInstanceId(delegateExecution.getProcessInstanceId());
        infringmentService.creatInfringementAction(infringement, delegateExecution.getProcessInstanceId(), delegateExecution.getVariable("adminNotes").toString(), InfringementActionType.INFRINGEMENT_CANCEL);
    }
}
