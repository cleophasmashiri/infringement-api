package com.zamaflow.bpm.api.service;

import com.zamaflow.bpm.api.domain.Notification;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class EmailDispatcher {

    private JmsTemplate jmsTemplate;

    private String destination;

    public EmailDispatcher(final JmsTemplate jmsTemplate, @Value("${jms.destination}") final String destination) {
        this.jmsTemplate = jmsTemplate;
        this.destination = destination;
    }

    public void send(Notification notification) {
        this.jmsTemplate.convertAndSend(destination, notification);
    }
    
}