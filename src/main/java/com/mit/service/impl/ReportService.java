package com.mit.service.impl;

import com.mit.domain.POFER03Client;
import com.mit.dto.request.ReportRequest;
import com.mit.exception.ResportException;
import com.mit.repository.IPOFER03ClientRepository;
import com.mit.service.IEmailContentBuilderService;
import com.mit.service.IEmailService;
import com.mit.service.IReportService;
import com.mit.util.EmailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.*;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class ReportService implements IReportService {

    private final IPOFER03ClientRepository repository;
    private final IEmailService emailService;
    private final IEmailContentBuilderService emailContentBuilderService;

    @Autowired
    public ReportService(
            IPOFER03ClientRepository repository,
            IEmailService emailService,
            IEmailContentBuilderService emailContentBuilderService) {
        this.repository = repository;
        this.emailService = emailService;
        this.emailContentBuilderService = emailContentBuilderService;
    }

    @Override
    public void sendCsv(ReportRequest request, String idClient) {
        Date startDate = Date.from(request.getCreationDate().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        Date endDate = Date.from(request.getUpdateDate().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        List<POFER03Client> pofer03Clients
                = this.repository.findClientsWithinDateRange(idClient, startDate, endDate);

        if (pofer03Clients == null || pofer03Clients.isEmpty())
            throw new ResportException("No se encontraron datos", "No se generó el archivo csv", "No se envió correo electrónico");

        byte[] csv = this.generateCSV(pofer03Clients);
        try {
            this.emailService.sendMailWithAttachment(
                    request.getEmail(),
                    "asunto del correo",
                    "reporte",
                    csv,
                    "reporte.csv");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Map<String, Object> getAcceptedAndAmount(String idClient) {
        return this.repository.getCountAndSum(idClient);
    }

    @Override
    public void sendEmailTemplate(Map<String, Object> request) {
        String email = (String) request.get("email");

        if (!EmailUtil.validate(email))
            throw new ResportException("El correo enviado no es válido");

        String id = (String) request.get("idOffer");

        if (!this.repository.existsByIdAndAccepted(id, 1))
            throw new ResportException("No se encontró el id de oferta y/o no es un crédito aceptado");

        String content = this.emailContentBuilderService.buildFromTemplate("template.ftl", request);
        try {
            this.emailService.sendMimeMessage(email, "Asunto del correo", content);
        } catch (MessagingException e) {
            throw new ResportException("Ocurrió un error al intentar enviar el correo");
        }
    }

    @Override
    public List<POFER03Client> getAll() {
        return this.repository.findAll();
    }

    private byte[] generateCSV(List<POFER03Client> transactionList) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(byteArrayOutputStream));

        try {
            // Write header
            writer.append("Concepto, Respuesta (aceptado), Tarjeta, FechayHora, Folio de seguimiento, NS_Intelipos, Afiliación del Comercio, Monto del cŕedito, Bonificación sin IVA comercio, Bonificación sin IVA getnet");
            writer.newLine();

            // Write data
            for (POFER03Client transaction : transactionList) {
                writer.append(transaction.getName()).append(',')
                        .append(transaction.getAccepted() == 1 ? "Aceptado" : "Rechazado").append(',')
                        .append(transaction.getCode()).append(',')
                        .append(transaction.getUpdateDate().toString()).append(',')
                        .append(transaction.getCode()).append(',');
                writer.newLine();
            }

            writer.flush();
            writer.close();
        } catch (IOException e) {
            // Handle exception
        }

        return byteArrayOutputStream.toByteArray();
    }
}
