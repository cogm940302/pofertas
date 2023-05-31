package com.mit.dto.response;

import lombok.Data;

import java.util.Date;

@Data
public class ClientResponse {

    private String id;
    private String idDevice;
    private String version;
    private String model;
    private String branch;
    private String company;
    private String username;
    private String afiliacion;
    private Integer terms;
    private Date creationDate;
    private Date updateDate;
}
