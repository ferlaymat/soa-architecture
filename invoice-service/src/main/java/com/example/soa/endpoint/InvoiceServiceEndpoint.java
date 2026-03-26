package com.example.soa.endpoint;

import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import soa.invoice.generated.*;


@Endpoint
public class InvoiceServiceEndpoint {

    //define the endpoint for the service invoice
    //Must correspond to the xsd namespace
    private static final String NAMESPACE = "http://soa/invoice";


    @PayloadRoot(namespace = NAMESPACE, localPart = "createInvoiceRequest")
    @ResponsePayload
    public CreateInvoiceResponse createInvoice(@RequestPayload CreateInvoiceRequest request) {
        //create the response object from xsd definition
        CreateInvoiceResponse response = new CreateInvoiceResponse();
        response.setId("F1");
        response.setClientId(request.getClientId());
        response.setAmount(request.getAmount());
        return response;
    }
}