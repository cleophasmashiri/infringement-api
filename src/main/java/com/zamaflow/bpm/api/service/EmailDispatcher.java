package com.zamaflow.bpm.api.service;

import com.zamaflow.bpm.api.domain.Notification;
import org.springframework.stereotype.Component;
import org.springframework.scheduling.annotation.Async;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class EmailDispatcher {

    private NotificationService notificationService;

    private static Logger LOGGER = LoggerFactory.getLogger(EmailDispatcher.class);

    public EmailDispatcher(final NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @Async
    public void send(Notification notification) {
        LOGGER.info("Sending email .. " + notification.toString());
        notificationService.sendMessage(notification);
    }
    
}