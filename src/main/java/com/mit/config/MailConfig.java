package com.mit.config;

import freemarker.cache.FileTemplateLoader;
import freemarker.cache.TemplateLoader;
import org.apache.commons.configuration2.Configuration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.MapMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactoryBean;

import java.io.File;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Properties;

@Service
public class MailConfig {

   // @Autowired
    private org.apache.commons.configuration2.Configuration config;


    private ApplicationContext context;

    private static final Logger LOGGER = LogManager.getLogger(MailConfig.class.getName());




    public FreeMarkerConfigurationFactoryBean getFreeMarkerConfiguration(){
        freemarker.template.Configuration configuration = new freemarker
                .template.Configuration(freemarker.template.Configuration.VERSION_2_3_20);

        FreeMarkerConfigurationFactoryBean configurer = new FreeMarkerConfigurationFactoryBean();
        try {
            String pathFile ="D:\\email";
            File fl = Paths.get(pathFile).toFile();
            TemplateLoader loader = new FileTemplateLoader(fl);
            configuration.setTemplateLoader(loader);
            configurer.setPreTemplateLoaders(loader);
            configurer.setPreferFileSystemAccess(true);
            configurer.setDefaultEncoding(StandardCharsets.UTF_8.name());
        }catch (Exception ex){
            LOGGER.error(ex.getMessage(),ex);
        }
        return configurer;
    }


    public JavaMailSenderImpl javaMailSender(){
        JavaMailSenderImpl sender = new JavaMailSenderImpl();
        sender.setDefaultEncoding(StandardCharsets.UTF_8.name());
       try {
           sender.setHost(config.getString("wpp.host"));
           sender.setPort(Integer.parseInt(config.getString("wpp.port")));
           sender.setUsername(config.getString("wpp.username"));
           sender.setPassword(config.getString("wpp.password"));
           sender.setDefaultEncoding("UTF-8");
           Properties properties = new Properties();
           properties.setProperty("mail.smtp.auth","true");
           properties.setProperty("mail.smtp.starttls.enable","true");
           properties.setProperty("mail.from",config.getString("mail.username"));
           sender.setJavaMailProperties(properties);
           sender.setProtocol("smtp");
       }catch (Exception ex){

       }

        return sender;
    }
}
