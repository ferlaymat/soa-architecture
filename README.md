# soa-architecture

This repository is split into 4 sub-projects, 3 services and 1 clients to simulate a Services Oriented Architecture.
They cover basics exchanges between themusing SOAP messages.It s the easiest way to get started with this architecture.

## Requirements
- Java 21
- Maven

## Getting Started

First, if needed, generate classes for each project  by using the command:
```bash
cd <project-path>
mvn generate-sources
```
Then, start each app via the command line:
```bash
cd <project-path>
mvn spring-boot:run
```

## Projects and URLs

| Service | URL | Use case |
|---|---|---|
| soa-client | http://localhost:8080 |Provides 2 basics REST calls to simulate a service bus(ESB) |
| client-service | http://localhost:8081 |Simple project to produce a ClientResponse object|
| invoice-service | http://localhost:8082 |Simple project to produce a InvoiceResponse object|
| camel-esb | http://localhost:8083 |ESB done by Camel routes|


