package com.mit.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.io.IOException;

@Configuration
public class FreeMarkerConfig {

    @Bean
    public FreeMarkerConfigurer freemarkerConfig() throws IOException {
        FreeMarkerConfigurer freeMarkerConfigurer = new FreeMarkerConfigurer();
        freeMarkerConfigurer.setTemplateLoaderPaths(
                "classpath:/templates/",
                "file:/home/isaac/Documents/projects/blmovil/templates/"
        );
        return freeMarkerConfigurer;
    }
}
