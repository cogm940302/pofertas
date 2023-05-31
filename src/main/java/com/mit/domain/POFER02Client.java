package com.mit.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "POFER02_CLIENT")
@Data
public class POFER02Client {

    @Id
    @Column(name = "cd_id", nullable = false, length = 50)
    private String id;

    @Column(name = "cd_idDevice", nullable = false, length = 100)
    private String idDevice;

    @Column(name = "tx_version", length = 50)
    private String version;

    @Column(name = "tx_model", length = 30)
    private String model;

    @Column(name = "cd_branch", length = 30)
    private String branch;

    @Column(name = "cd_company", length = 30)
    private String company;

    @Column(name = "tx_username", length = 30)
    private String username;

    @Column(name = "tx_afiliacion", length = 15)
    private String afiliacion;

    @Column(name = "st_terms")
    private Integer terms;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fh_creation")
    private Date creationDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fh_update")
    private Date updateDate;
}
