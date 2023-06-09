package com.mit.service;

import javax.mail.MessagingException;

public interface IEmailService {

    void sendMailWithAttachment(String to, String subject, String text, byte[] attachment, String attachmentName) throws MessagingException;
    void sendMimeMessage(String to, String subject, String content) throws MessagingException;
}
