package com.example.camel.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import soa.generated.CreateInvoiceRequest;

public class CreateInvoiceRequestProcessor implements Processor {
    @Override
    public void process(Exchange exchange) {
        String clientId = exchange.getIn().getHeader("clientId", String.class);
        Double amount = exchange.getIn().getHeader("amount", Double.class);

        CreateInvoiceRequest request = new CreateInvoiceRequest();
        request.setClientId(clientId);
        request.setAmount(amount);
        exchange.getIn().setBody(request);
    }
}