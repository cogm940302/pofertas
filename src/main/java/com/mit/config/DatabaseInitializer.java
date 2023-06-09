package com.mit.config;

import javax.annotation.PostConstruct;

import com.mit.domain.POFER03Client;
import com.mit.repository.IPOFER03ClientRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@Component
public class DatabaseInitializer {

    private final IPOFER03ClientRepository repository;

    public DatabaseInitializer(IPOFER03ClientRepository repository) {
        this.repository = repository;
    }

    @PostConstruct
    @Transactional
    public void initData() throws ParseException {
        this.repository.save(this.buildPOFER03Client01());
        this.repository.save(this.buildPOFER03Client02());
        this.repository.save(this.buildPOFER03Client03());
    }

    private POFER03Client buildPOFER03Client01() throws ParseException {
        POFER03Client client = new POFER03Client();
        client.setId("8ca735ff-c768-45a3-8ff8-7357fb53e275");
        client.setIdClient("8cc60a20-cbbb-4bfe-939a-b63125afb319");
        client.setBin("557907");
        client.setMcc("3089");
        client.setServiceIntelipos(0);
        client.setMerchant("1234567890");
        client.setKey("5a6b55989667fc294bdd809a6f91489f40440722a373a9c743bc63a7a1592c28-772c537f-1db6-407d-9d7e-49b17b3a4660");
        client.setCode("10147485347");
        client.setName("Crédito preaprobado.");
        client.setTotalAmount(BigDecimal.valueOf(51000));
        client.setNetAmount(BigDecimal.valueOf(50175.97));
        client.setAccepted(0);

        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yy");

        String creationDateString = "29/05/23";
        client.setCreationDate(format.parse(creationDateString));

        String updateDateString = "29/05/23";
        client.setUpdateDate(format.parse(updateDateString));
        return client;
    }

    private POFER03Client buildPOFER03Client02() throws ParseException {
        POFER03Client client = new POFER03Client();
        client.setId("c446a178-8ada-4cfc-97fa-288a6c84ca90");
        client.setIdClient("a2d57cbe-5cde-4ed1-8c31-b59d5269b5d5");
        client.setBin("557907");
        client.setMcc("3089");
        client.setServiceIntelipos(0);
        client.setMerchant("1234567890");
        client.setKey("5a6b55989667fc294bdd809a6f91489f40440722a373a9c743bc63a7a1592c28-eaab4c39-a29a-466d-acd3-0da9d1176048");
        client.setCode("10147485347");
        client.setName("Crédito preaprobado.");
        client.setTotalAmount(BigDecimal.valueOf(51000));
        client.setNetAmount(BigDecimal.valueOf(50175.97));
        client.setAccepted(1);

        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yy");

        String creationDateString = "29/05/23";
        client.setCreationDate(format.parse(creationDateString));

        String updateDateString = "29/05/23";
        client.setUpdateDate(format.parse(updateDateString));
        return client;
    }

    private POFER03Client buildPOFER03Client03() throws ParseException {
        POFER03Client client = new POFER03Client();
        client.setId("79f6c635-3b88-4db2-ba9e-bba517a56929");
        client.setIdClient("a2d57cbe-5cde-4ed1-8c31-b59d5269b5d5");
        client.setBin("557907");
        client.setMcc("3089");
        client.setServiceIntelipos(0);
        client.setMerchant("1234567890");
        client.setKey("5a6b55989667fc294bdd809a6f91489f40440722a373a9c743bc63a7a1592c28-80d4d71a-cd96-49b1-9252-631401bc8467");
        client.setCode("10147485347");
        client.setName("Crédito preaprobado.");
        client.setTotalAmount(BigDecimal.valueOf(51000));
        client.setNetAmount(BigDecimal.valueOf(50175.97));
        client.setAccepted(1);

        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yy");

        String creationDateString = "06/06/23";
        client.setCreationDate(format.parse(creationDateString));

        String updateDateString = "06/06/23";
        client.setUpdateDate(format.parse(updateDateString));
        return client;
    }
}
