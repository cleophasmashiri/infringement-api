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
public class DriverNominationDelegate implements JavaDelegate {

    @Autowired
    private InfringementService infringementService;

    private final Logger LOGGER = LoggerFactory.getLogger(DriverNominationDelegate.class);

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        LOGGER.info("DriverNomination");
        Infringement infringement =  infringementService.getInfringmentByprocessInstanceId(delegateExecution.getProcessInstanceId());
        infringementService.creatInfringementAction(infringement, delegateExecution.getProcessInstanceId(), delegateExecution.getVariable("driverNotes").toString(), InfringementActionType.INFRINGEMENT_DRIVER_NOMINATION, 0.0, 0);
    }
}
