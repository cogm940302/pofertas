package com.mit.dto.request;

import lombok.Data;

@Data
public class ClientRequest {

    private String version;
    private String model;
    private String branch;
    private String company;
    private String username;
    private String afiliacion;
}
