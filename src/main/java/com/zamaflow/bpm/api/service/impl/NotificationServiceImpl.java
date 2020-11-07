package com.zamaflow.bpm.api.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import com.zamaflow.bpm.api.domain.Notification;
import com.zamaflow.bpm.api.service.NotificationService;

/**
 * Created by cleophas on 2018/10/21.
 */

@Service
public class NotificationServiceImpl implements NotificationService {

    private static Logger LOGGER = LoggerFactory.getLogger(NotificationServiceImpl.class);

    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    private MailContentBuilder mailContentBuilder;

    @Override
    public void sendMessage(final Notification notificationMessage) {

        LOGGER.info("notifyLead .. " + notificationMessage.getBody());

        final MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = null;
        try {
        	boolean hasAttachment = notificationMessage.getAttachments()!=null && notificationMessage.getAttachments().size() > 0;
            helper = new MimeMessageHelper(message, hasAttachment);
            helper.setFrom(notificationMessage.getToFrom());
            helper.setTo(notificationMessage.getToEmail());
            helper.setSubject(notificationMessage.getSubject());
            final String content = mailContentBuilder.build(notificationMessage);
            helper.setText(content, true);
            if (hasAttachment) {         	
            	for (File attachment: notificationMessage.getAttachments()) {
            		FileSystemResource file = new FileSystemResource(attachment);
            		helper.addAttachment(notificationMessage.getSubject() + ".pdf", file);
            	}		
            }
        } catch (final MessagingException e) {
            LOGGER.error("Error .. " + e );
        }
        emailSender.send(message);
    }
}