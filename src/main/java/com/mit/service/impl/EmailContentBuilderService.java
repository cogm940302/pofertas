package com.mit.service.impl;

import com.mit.exception.ResportException;
import com.mit.service.IEmailContentBuilderService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;

@Service
public class EmailContentBuilderService implements IEmailContentBuilderService {

    private final Configuration freemarkerConfig;

    public EmailContentBuilderService(Configuration freemarkerConfig) {
        this.freemarkerConfig = freemarkerConfig;
    }

    @Override
    public String buildFromTemplate(String templateName, Map<String, Object> model) {
        Writer writer = new StringWriter();
        try {
            Template template = freemarkerConfig.getTemplate(templateName);
            template.process(model, writer);
        } catch (TemplateException | IOException e) {
            throw new ResportException(e.getMessage(), "Ocurrió un error al procesar la plantilla de correo electrónico");
        }
        return writer.toString();
    }
}
