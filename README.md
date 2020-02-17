# Spring Boot Mailer Service

Spring Boot Mail Service that sends mail using Apache Kafka Streaming Platform.

## Before Start

-   Installation commands listed below runs applications in development mode.
-   Application runs on port 8081.
-   Apache Kafka is used to send large number of mails.

## Pre-Requisites

> -   Minimum Java 8 required to run application.
> -   Edit application.yml file in resources folder for sender mail config (spring.mail) and kafka connection (spring.kafka).
> -   Mail send attempt count can be set via application.yml config file if operation fails.

## Installation

-   `git clone https://github.com/harunozceyhan/mailer.git`
-   `cd mailer`
-   `"{path-to-project}/mvnw" package`
-   `"{path-to-project}/mvnw" spring-boot:run`

## App URL

-   [http://localhost:8081/](http://localhost:8081/)

## App Swagger UI URL

-   [http://localhost:8081/swagger-ui.html](http://localhost:8081/swagger-ui.html)

## Technologies Used

-   Dev

    -   Java 8
    -   Spring Boot 2.2.4
    -   Spring Email
    -   Lombok
    -   Spring Retry
    -   Apache Commons
    -   Spring Kafka
    -   Springdoc Open API
