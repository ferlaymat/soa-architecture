package com.example.soa.endpoint;

import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import soa.client.generated.*;

@Endpoint
public class ClientServiceEndpoint {

    //define the endpoint for the service client
    //Must correspond to the xsd namespace
    private static final String NAMESPACE = "http://soa/client";


    @PayloadRoot(namespace = NAMESPACE, localPart = "getClientRequest")
    @ResponsePayload
    public GetClientResponse getClient(@RequestPayload GetClientRequest request) {
        //create the response object from xsd definition
        GetClientResponse response = new GetClientResponse();
        response.setId(request.getId());
        response.setName("Toto Foo");
        response.setEmail("tfoo@mail.com");
        return response;
    }
}