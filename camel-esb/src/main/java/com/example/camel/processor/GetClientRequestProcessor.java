package com.example.camel.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import soa.generated.GetClientRequest;

public class GetClientRequestProcessor implements Processor {
    @Override
    public void process(Exchange exchange) {
        String id = exchange.getIn().getHeader("id", String.class);
        GetClientRequest request = new GetClientRequest();
        request.setId(id);
        exchange.getIn().setBody(request);
    }
}