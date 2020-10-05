package com.zamaflow.bpm.api.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.scheduling.annotation.Async;

@Component
public class SmsSenderImpl {

    private static Logger LOGGER = LoggerFactory.getLogger(SmsSenderImpl.class);
  

    @Async
    public void sendSms(String content, String toCellPhoneNumber) {

        LOGGER.info("Sending sms .. " + content + " to: " + toCellPhoneNumber);

    }

}