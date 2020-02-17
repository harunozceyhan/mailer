# Spring Boot Mailer Service

Spring Boot Mail Service that sends mail using Apache Kafka Streaming Platform.

## Before Start

-   Installation commands listed below runs applications in development mode.
-   Application runs on port 8081.
-   Apache Kafka is used to send large number of mails.
-   Only one attachment allowed to send.

## Pre-Requisites

> -   Minimum Java 8 required to run application.
> -   Edit application.yml file in resources folder for sender mail config (spring.mail) and kafka connection (spring.kafka).
> -   Mail send attempt count can be set via application.yml config file if operation fails.

## Installation & Running

-   `git clone https://github.com/harunozceyhan/mailer.git`
-   `cd mailer`
-   `"{path-to-project}/mvnw" package`
-   `"{path-to-project}/mvnw" spring-boot:run`

## Run Tests

-   `"{path-to-project}/mvnw" test`

## App Swagger UI URL

-   [http://localhost:8081/swagger-ui.html](http://localhost:8081/swagger-ui.html)

## Example Send Mail Post Request

```bash
curl -X POST "http://localhost:8081/mail" -H "accept: application/json" -H "Content-Type: application/json" -d "{\"to\":\"username@mail.com\",\"subject\":\"Example Subject\",\"text\":\"Example Content\",\"attachmentUri\":\"url-to-attachment\"}"
```

## Technologies Used

-   Java 8
-   Spring Boot 2.2.4
-   Spring Email
-   Lombok
-   Spring Retry
-   Apache Commons
-   Spring Kafka
-   Springdoc Open API
-   JUnit
-   Mockito
-   Spring Kafka Test
