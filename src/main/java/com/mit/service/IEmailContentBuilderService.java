package com.mit.service;

import java.util.Map;

public interface IEmailContentBuilderService {

    String buildFromTemplate(String templateName, Map<String, Object> model);
}
