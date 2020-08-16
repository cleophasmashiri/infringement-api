package com.zamaflow.bpm.api.service;

import com.zamaflow.bpm.api.domain.Notification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class EmailReceiver {

    private String destination;

    private NotificationService notificationService;

    @Autowired
    public EmailReceiver(@Value("${jms.destination}") final String destination, final NotificationService notificationService) {
        this.destination = destination;
        this.notificationService = notificationService;
    }

    @JmsListener(destination = "emails", containerFactory = "myFactory")
	public void receiveMessage(Notification notification) {
        notificationService.sendMessage(notification);
	}
    
}