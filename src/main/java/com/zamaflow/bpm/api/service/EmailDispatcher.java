package com.zamaflow.bpm.api.service;

import com.zamaflow.bpm.api.domain.Notification;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.scheduling.annotation.Async;

@Component
public class EmailDispatcher {

    private NotificationService notificationService;

    public EmailDispatcher(final NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @Async
    public void send(Notification notification) {
        notificationService.sendMessage(notification);
    }
    
}