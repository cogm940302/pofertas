package com.mit.util.mail;

import com.mit.commons.request.MailNotification;
import org.springframework.scheduling.annotation.Async;

import java.util.List;
import java.util.Optional;

public interface MailServ {

    Optional<Boolean> normalSending(MailNotification mailNotification);

    @Async
    void asyncSending(MailNotification mailNotification);

    @Async
    void massiveSending(List<MailNotification> lsMail);

}
