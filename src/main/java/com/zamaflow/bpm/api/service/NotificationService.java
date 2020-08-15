package com.zamaflow.bpm.api.service;

import com.zamaflow.bpm.api.domain.Notification;

public interface NotificationService {

    void sendMessage(Notification message);
    
}