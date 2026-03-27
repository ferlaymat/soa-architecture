package com.example.soa.client.controller;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.ws.client.core.WebServiceTemplate;
import soa.generated.CreateInvoiceRequest;
import soa.generated.CreateInvoiceResponse;
import soa.generated.GetClientRequest;
import soa.generated.GetClientResponse;

import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class ApiController {

    private final WebServiceTemplate template;
    private final RestTemplate restTemplate;

    @Autowired
    public ApiController(WebServiceTemplate template, RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        this.template = template;
    }

    @GetMapping("/client")
    public String getClient() {
        GetClientRequest req = new GetClientRequest();
        req.setId("1");

        GetClientResponse res = (GetClientResponse) template.marshalSendAndReceive(
                "http://localhost:8081/ws",
                req
        );
        return String.format("Client - %s: %s, email: %s", res.getId(), res.getName(), res.getEmail());
    }

    @GetMapping("/client/camel")
    public String getClientCamel() throws JAXBException {
        String response = this.restTemplate.getForObject("http://localhost:8083/camel/api/esb/getClient?id=CAM1", String.class);

        JAXBContext context = JAXBContext.newInstance(GetClientResponse.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        GetClientResponse res = (GetClientResponse) unmarshaller.unmarshal(new StringReader(response));
        return String.format("Client - %s: %s, email: %s", res.getId(), res.getName(), res.getEmail());


    }

    @GetMapping("/invoice")
    public String getInvoice() {
        CreateInvoiceRequest freq = new CreateInvoiceRequest();
        freq.setClientId("C001");
        freq.setAmount(149.90);

        CreateInvoiceResponse res = (CreateInvoiceResponse) template.marshalSendAndReceive(
                "http://localhost:8082/ws",
                freq
        );
        return String.format("Invoice : %s", res.getAmount());
    }

    @GetMapping("/invoice/camel")
    public String getInvoiceCamel() throws JAXBException {

        String response = this.restTemplate.getForObject("http://localhost:8083/camel/api/esb/createInvoice?clientId=CAM1&amount=149.90", String.class);

        JAXBContext context = JAXBContext.newInstance(CreateInvoiceResponse.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        CreateInvoiceResponse res = (CreateInvoiceResponse) unmarshaller.unmarshal(new StringReader(response));
        return String.format("Invoice : %s", res.getAmount());
    }
}
