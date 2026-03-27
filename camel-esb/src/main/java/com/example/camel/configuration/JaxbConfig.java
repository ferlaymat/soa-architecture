package com.example.camel.configuration;


import org.apache.camel.model.dataformat.JaxbDataFormat;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class JaxbConfig {

    @Bean
    public JaxbDataFormat jaxbClient() {
        JaxbDataFormat jaxb = new JaxbDataFormat();
        jaxb.setContextPath("soa.generated");
        return jaxb;
    }

    @Bean
    public JaxbDataFormat jaxbInvoice() {
        JaxbDataFormat jaxb = new JaxbDataFormat();
        jaxb.setContextPath("soa.generated");
        return jaxb;
    }
}