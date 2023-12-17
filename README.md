# Stock-service

## General Information

* The **Stock Service** is a JVM-based backend application built with **Spring Boot**, **Hibernate**, **PostgreSQL**, **Springdoc-openApi**, **Docker Compose**, **JUnit 5**, and **YAML** property configuration.
* The **Stock Service** uses [JUnit 5](https://junit.org/junit5/) to run test database and Springboot integration tests.
* The **Stock Service** uses [springdoc-openapi](https://springdoc.org/) to generate OpenAPI documentation
* The **Stock Service** exposes a RESTful API to manage stocks
* The **Stock Service** can be run locally with **docker-compose** (no local PostgreSQL installation required)

## Database Schema

      column      |    type   |    description
    --------------|-----------|-----------------------------
    id            | integer   | stock id, primary key
    --------------|-----------|-----------------------------
    name          | text      | stock company name, unique
    --------------|-----------|-----------------------------
    current_price | numeric   | stock price
    --------------|-----------|-----------------------------
    last_update   | timestamp | timestamp with timezone

## Stock Service API

                       |       API endpoint        |    curl example
    -------------------|---------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------
    Get list of Stocks | GET '/api/stocks'         | curl -X GET 'localhost:8080/stock-Service/api/stocks?page=0&size=40&sort=name'
    -------------------|---------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------
    Get Stock by id    | GET '/api/stocks/{id}'    | curl -X GET 'localhost:8080/stock-Service/api/stocks/1'
    -------------------|---------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------
    Create new Stock   | POST '/api/stocks'        | curl -X POST 'localhost:8080/stock-Service/api/stocks' -H 'Content-Type: application/json' --data-raw '{"name": "BNB", "currentPrice": 100}'
    -------------------|---------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------
    Update Stock price | PATCH '/api/stocks/{id}'  | curl -X PATCH 'localhost:8080/stock-Service/api/stocks/11?price=34'
    -------------------|---------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------
    Delete Stock       | DELETE '/api/stocks/{id}' | curl -X DELETE 'localhost:8080/stock-Service/api/stocks/2'

## Prerequisites
- Java Development Kit (JDK) 17 or higher (https://www.java.com).
- Apache Maven (http://maven.apache.org).
- Git Client (http://git-scm.com).
- Docker (http://docker.com).
- Docker Compose (https://docs.docker.com/compose/).

## Getting Started
1. clone **Stock Service** repository:
   ```bash
   git clone https://github.com/skayvanfar/stock-service.git
   cd stock-service

2. Build **Stock Service** application and run tests:
   ```bash
   ./mvnw clean install

3. Start the Postgresql database and run stock-service application using Docker Compose:
    ```bash
    docker-compose -f compose/common/docker-compose.yml up -d

4. Open **Stock Service** OpenAPI documentation
   [swagger ui](http://localhost:8080/stock-service/swagger-ui.html)

5. Test **Stock Service** application (see *curl example* in [Stock Service API](#stock-service-api))

6. [spring boot actuator](http://localhost:8080/stock-service/actuator)


