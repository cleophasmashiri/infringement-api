package com.zamaflow.bpm.api.service.impl;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import com.zamaflow.bpm.api.domain.SmsMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;

@Component
public class SmsSenderImpl {

    // Find your Account Sid and Token at twilio.com/user/account
    @Value("${sms.smsclientid}")
    public String ACCOUNT_SID;

    @Value("${sms.smskey}")
    public String AUTH_TOKEN;

    @Value("${sms.fromPhoneNumber}")
    private String fromPhoneNumber;

    private static Logger LOGGER = LoggerFactory.getLogger(SmsSenderImpl.class);

    public void SmsSenderImpl() {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
    }

    @Async
    public void sendSms(SmsMessage sms) {
        LOGGER.info("Sending sms .. " + sms.getSmsContent() + " to: " + sms.getCellPhoneNumber());

        Message.creator(new PhoneNumber(sms.getCellPhoneNumber()), 
        new PhoneNumber(fromPhoneNumber),
        sms.getSmsContent()).create();

    }

}