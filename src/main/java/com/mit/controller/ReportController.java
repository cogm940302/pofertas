package com.mit.controller;

import com.mit.domain.POFER03Client;
import com.mit.dto.request.ReportRequest;
import com.mit.service.IReportService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/report")
public class ReportController {

    private final IReportService service;

    public ReportController(IReportService service) {
        this.service = service;
    }

    @PostMapping("/csv")
    public ResponseEntity<Void> csv(
            @RequestBody ReportRequest reportRequest,
            @RequestHeader("Authorization") String client) {
        this.service.sendCsv(reportRequest, client);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/accept-amount")
    public ResponseEntity<Map<String, Object>> acceptAmount(@RequestHeader("Authorization") String client) {
        return ResponseEntity.ok(this.service.getAcceptedAndAmount(client));
    }

    @PostMapping("/template")
    public ResponseEntity<Void> template(@RequestBody Map<String, Object> request) {
        this.service.sendEmailTemplate(request);
        return ResponseEntity.ok().build();
    }

    @GetMapping()
    public ResponseEntity<List<POFER03Client>> getAll() {
        return ResponseEntity.ok(this.service.getAll());
    }
}
