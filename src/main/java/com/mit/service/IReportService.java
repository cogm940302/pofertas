package com.mit.service;

import com.mit.domain.POFER03Client;
import com.mit.dto.request.ReportRequest;

import java.util.List;
import java.util.Map;

public interface IReportService {

    void sendCsv(ReportRequest request, String idClient);
    Map<String, Object> getAcceptedAndAmount(String idClient);

    void sendEmailTemplate(Map<String, Object> request);
    List<POFER03Client> getAll();
}
