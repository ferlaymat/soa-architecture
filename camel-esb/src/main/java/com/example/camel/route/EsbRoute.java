package com.example.camel.route;

import com.example.camel.processor.CreateInvoiceRequestProcessor;
import com.example.camel.processor.GetClientRequestProcessor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.apache.camel.model.rest.RestParamType;
import org.springframework.stereotype.Component;

@Component
public class EsbRoute extends RouteBuilder {

    @Override
    public void configure() {

        // --- Route 0 : Global error advice ---
        errorHandler(
                deadLetterChannel("direct:error")
                        .maximumRedeliveries(3)
                        .redeliveryDelay(1000)
                        .logExhausted(true)
        );

        restConfiguration()
                .component("servlet")
                .contextPath("/")
                .bindingMode(RestBindingMode.json);

        rest("/api/esb")
                .get("/getClient")
                .param()
                .name("id")
                .type(RestParamType.query)
                .required(true)
                .endParam()
                .to("direct:getClient")

                .get("/createInvoice")
                .param()
                .name("clientId")
                .type(RestParamType.query)
                .required(true)
                .endParam()
                .param()
                .name("amount")
                .type(RestParamType.query)
                .required(true)
                .endParam()
                .to("direct:createInvoice");

        from("direct:error")
                .log("ESB - Error : ${exception.message}")
                .process(exchange -> {
                    Exception e = exchange.getProperty(
                            org.apache.camel.Exchange.EXCEPTION_CAUGHT,
                            Exception.class
                    );
                    System.err.println("Erreur ESB : " + e.getMessage());
                });


        // --- Route 1 :  clientService ---
        from("direct:getClient")
            .log("ESB - call ClientService")
            .process(new GetClientRequestProcessor())
            .marshal().jaxb("soa.generated")
            .toD("spring-ws:http://localhost:8081/ws?soapAction=http://soa/client/getClientRequest")
            .unmarshal().jaxb("soa.generated");


        // --- Route 2 : invoiceService ---
        from("direct:createInvoice")
            .log("ESB - call InvoiceService")
            .process(new CreateInvoiceRequestProcessor())
            .marshal().jaxb("soa.generated")
            .toD("spring-ws:http://localhost:8082/ws?soapAction=http://soa/invoice/createInvoiceRequest")
            .unmarshal().jaxb("soa.generated");



    }
}