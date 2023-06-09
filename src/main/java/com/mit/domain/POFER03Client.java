package com.mit.domain;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "POfer03_CLIENT")
@Data
public class POFER03Client {

    @Id
    @Column(name = "CD_ID", nullable = false, length = 50)
    private String id;

    @Column(name = "CD_IDCLIENT", nullable = false, length = 50)
    private String idClient;

    @Column(name = "CD_BIN", length = 9)
    private String bin;

    @Column(name = "CD_MCC", nullable = false, length = 4)
    private String mcc;

    @Column(name = "CD_SERVICE_INTELIPOS")
    private Integer serviceIntelipos;

    @Column(name = "TX_MERCHANT", length = 12)
    private String merchant;

    @Column(name = "TX_KEY", nullable = false, length = 200)
    private String key;

    @Column(name = "TX_CODE", length = 50)
    private String code;

    @Column(name = "TX_NAME", length = 50)
    private String name;

    @Column(name = "NU_TOTAL_AMOUNT")
    private BigDecimal totalAmount;

    @Column(name = "NU_NET_AMOUNT")
    private BigDecimal netAmount;

    @Column(name = "ST_ACCEPTED")
    private Integer accepted;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "FH_CREATION")
    private Date creationDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "FH_UPDATE")
    private Date updateDate;
}
