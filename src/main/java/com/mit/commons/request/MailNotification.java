package com.mit.commons.request;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.Serializable;
import java.sql.Date;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

public class MailNotification implements Serializable {

    private String message;
    private String to;
    private String subject;
    private Map<String,Object> dataMailTemplate;

    public MailNotification(){
        this.dataMailTemplate=new HashMap<>();
    }

    public MailNotification(String message, String to, String subject, Map<String, Object> dataMailTemplate) {
        this.message = message;
        this.to = to;
        this.subject = subject;
        this.dataMailTemplate = dataMailTemplate;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Map<String, Object> getDataMailTemplate() {
        return dataMailTemplate;
    }

    public void setDataMailTemplate(Map<String, Object> dataMailTemplate) {
        this.dataMailTemplate = dataMailTemplate;
    }

    public static final BiFunction<MailNotification, MimeMessage,MimeMessage> toMimeMessage = (notification,message) -> {
        try {
            message.setSentDate(Date.from(Instant.now()));
            message.setText(notification.getMessage());
            message.setRecipient(Message.RecipientType.TO,new InternetAddress(notification.getTo()));
            message.setSubject(notification.getSubject());
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        return message;
    };
}
