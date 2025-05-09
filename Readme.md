# E-Store Application

## Overview
The E-Store application is a RESTful service designed to manage and calculate the pricing of shopping carts. It is built using **Spring Boot**, **Gradle**, and follows modern development practices such as validation, exception handling, and OpenAPI documentation.

## Features
- **Cart Pricing**: Calculate the total price of a shopping cart.
- **Validation**: Input validation using `jakarta.validation`.
- **Exception Handling**: Centralized error handling with `@ControllerAdvice`.
- **API Documentation**: Swagger UI and OpenAPI 3.0 support for API exploration.

## Technologies
- **Java 21**
- **Spring Boot 3.4.2**
- **Gradle**
- **SpringDoc OpenAPI**
- **Lombok**
- **MapStruct**

## Prerequisites
- Java 21 or higher
- Gradle 7.6 or higher
- Maven Central repository access

## Setup and Run
1. Clone the repository:
   ```bash
   git clone https://github.com/your-repo/e-store.git
   cd e-store
   Build the project:  
   ./gradlew clean build
   Run the application:  
   ./gradlew bootRun

## Access the application:  
- Swagger UI: http://localhost:8080/swagger-ui/index.html