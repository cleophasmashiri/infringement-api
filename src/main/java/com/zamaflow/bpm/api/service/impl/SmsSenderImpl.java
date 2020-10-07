package com.zamaflow.bpm.api.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import com.zamaflow.bpm.api.domain.SmsMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;

@Component
public class SmsSenderImpl {

    private static Logger LOGGER = LoggerFactory.getLogger(SmsSenderImpl.class);
    
    @Autowired
    private RestTemplate restTemplate;
    
    // @Value("${sms.url}")
	private String smsUrl;
  
    @Async
	public void sendSms(SmsMessage sms) {
		 LOGGER.info("Sending sms .. " + sms.getSmsContent() + " to: " + sms.getCellPhoneNumber());
		 
		 restTemplate.postForEntity(smsUrl, sms, String.class);
	
	}

}