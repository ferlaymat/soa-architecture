package com.example.soa.client.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.ws.client.core.WebServiceTemplate;
import soa.generated.*;

@RestController
@RequestMapping("/api/v1")
public class ApiController {

    private final WebServiceTemplate template;

    @Autowired
    public ApiController(WebServiceTemplate template) {
        this.template = template;
    }

    @GetMapping("/client")
    public String getClient(){
        GetClientRequest req = new GetClientRequest();
        req.setId("C001");

        GetClientResponse res = (GetClientResponse) template.marshalSendAndReceive(
                "http://localhost:8081/ws",
                req
        );
        return String.format("Client - %s: %s, email: %s" , res.getId(), res.getName(),res.getEmail());
    }

    @GetMapping("/invoice")
    public String getInvoice(){
        CreateInvoiceRequest freq = new CreateInvoiceRequest();
        freq.setClientId("C001");
        freq.setAmount(149.90);

        CreateInvoiceResponse res = (CreateInvoiceResponse) template.marshalSendAndReceive(
                "http://localhost:8082/ws",
                freq
        );
        return String.format("Invoice : %s" , res.getAmount());
    }
}
