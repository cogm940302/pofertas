package com.mit.util.mail;

import com.mit.commons.request.MailNotification;
import com.mit.config.MailConfig;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactoryBean;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.stream.Collectors;

@Service
public class MailService implements MailServ{

    private final Configuration freeMarkerConfigurer;


    private final JavaMailSender mailSender;


    private final MailConfig config;

     @Autowired
    public MailService(MailConfig config){
         this.config = config;
        this.mailSender = config.javaMailSender();
        this.freeMarkerConfigurer = config.getFreeMarkerConfiguration().getObject();
    }

    @Override
    public Optional<Boolean> normalSending(MailNotification mailNotification) {
        Optional<Boolean> result;
        MimeMessage message = this.mailSender.createMimeMessage();
      try {
          message.setRecipient(Message.RecipientType.TO
                  ,new InternetAddress(mailNotification.getTo()));
          message.setSentDate(Date.from(Instant.now()));
          message.setSubject(mailNotification.getSubject());
          message.setText(Objects.isNull(mailNotification.getMessage()) ? "" : mailNotification.getMessage());
          if(mailNotification.getDataMailTemplate().containsKey("template")) configureTemplateMessage(mailNotification
                  .getDataMailTemplate(),message);
          this.mailSender.send(message);
          result = Optional.of(Boolean.TRUE);
      } catch (MessagingException | TemplateException | IOException e) {
          throw new RuntimeException(e);
      }
        return result;
    }

    @Override
    public void asyncSending(MailNotification mailNotification) {
        CompletableFuture.supplyAsync(()->normalSending(mailNotification), Executors.newSingleThreadScheduledExecutor());
    }

    @Override
    public void massiveSending(List<MailNotification> lsMail) {
        List<MimeMessage> massive =  lsMail.stream().map(notification -> {
            MimeMessage message = MailNotification.toMimeMessage.apply(notification,this.mailSender.createMimeMessage());
            try {
                if(notification.getDataMailTemplate().containsKey("template")) configureTemplateMessage(notification.getDataMailTemplate(),message);
            }catch (Exception ex){

            }
            return  message;
        }).collect(Collectors.toList());
        this.mailSender.send(massive.toArray(new MimeMessage[massive.size()]));
    }

    private void configureTemplateMessage(Map<String,Object> data, MimeMessage msg) throws MessagingException
            , TemplateException, IOException {
        MimeMessageHelper helper = new MimeMessageHelper(msg
                ,MimeMessageHelper.MULTIPART_MODE_MIXED, StandardCharsets.UTF_8.name());
        helper.setText(htmlFromTemplate((String)data.get("template")
                ,(Map<String,Object>)data.get("template_data")),true);
    }
    private String htmlFromTemplate(String path,Map<String,Object> dataEmail) throws TemplateException, IOException {
        String html = "";
        Template template = this.freeMarkerConfigurer.getTemplate(path);
        html = FreeMarkerTemplateUtils.processTemplateIntoString(template,dataEmail);
        return html;
    }
}
