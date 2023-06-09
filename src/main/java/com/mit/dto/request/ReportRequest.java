package com.mit.dto.request;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ReportRequest {

    private String email;
    private LocalDate creationDate;
    private LocalDate updateDate;
}
