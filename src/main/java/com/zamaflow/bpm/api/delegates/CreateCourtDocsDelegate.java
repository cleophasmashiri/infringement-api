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
public class CreateCourtDocsDelegate implements JavaDelegate {

    private final Logger LOGGER = LoggerFactory.getLogger(CreateCourtDocsDelegate.class);

    @Autowired
    private InfringementService infringementService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        delegateExecution.setVariable("passedCreditCheck", Boolean.TRUE);
        LOGGER.info("NewInfrigment sent");
        Infringement infringement =  infringementService.getInfringmentByprocessInstanceId(delegateExecution.getProcessInstanceId());
        infringementService.creatInfringementAction(infringement, delegateExecution.getProcessInstanceId(), delegateExecution.getVariable("adminNotes").toString(), InfringementActionType.INFRINGEMENT_CREATED_COURT_CASE, 0.0, 0);
    }
}
