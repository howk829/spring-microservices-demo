# Spring Microservices Architecture Demo

This project demonstrates a **Microservices Architecture** built using **Spring Boot**, **Spring Cloud**, and related technologies. It includes multiple microservices, an API Gateway, distributed transaction management, and centralized logging and monitoring with the ELK stack.

---
## Table of Contents

- [Overview](#overview)
- [Project Scope](#project-scope)
- [Architecture Overview](#architecture-overview)
  - [Architecture Diagram](#architecture-diagram)
  - [Key Components](#key-components)
- [Features](#features)
  - [Core Features](#core-features)
- [Technical Stack](#technical-stack)
- [Installation and Setup](#installation-and-setup)
  - [Prerequisites](#prerequisites)
  - [Steps to Run the Project](#steps-to-run-the-project)
- [Viewing Logs in Kibana](#viewing-logs-in-kibana)
- [API Endpoints](#api-endpoints)
  - [User Service](#user-service)
  - [Order Service](#order-service)
  - [Payment Service](#payment-service)
  - [API Gateway](#api-gateway)
- [Folder Structure](#folder-structure)
- [Future Enhancements](#future-enhancements)
- [Contributing](#contributing)
- [License](#license)


## Project Scope

The project showcases:
1. **Microservices**: Independent services for specific responsibilities:
   - `User Service`: Manages user-related data and operations.
   - `Order Service`: Handles order-related operations. (Still under devlopement)
   - `Payment Service`: Manages payment processing. (Still under devlopement)
2. **API Gateway**: Provides a unified entry point for external clients with JWT-based security and load balancing.
3. **Service Discovery**: Uses Eureka for dynamic service registration and discovery.
4. **Centralized Configuration**: Manages service configurations with Spring Cloud Config Server.
5. **Distributed Transaction Management**: Uses the SAGA pattern for consistency across microservices.
6. **Messaging Queue**: Uses Kafka for asynchronous communication between services.
7. **Logging and Monitoring**: Aggregates logs using the ELK stack (Elasticsearch, Logstash, Kibana).
8. **Databases**:
   - Relational Database (PostgreSQL).
   - NoSQL Database (MongoDB).
   - In-Memory Database (Redis).

---

## Architecture Overview

### Architecture Diagram
![Microservices Architecture](https://github.com/howk829/spring-microservices-demo/blob/main/acrhitecture_diagram.png?raw=true)


## Folder Structure
```bash
microservices-architecture/
├── user-service/          # User Service code
├── order-service/         # Order Service code
├── payment-service/       # Payment Service code
├── api-gateway/           # Spring Cloud Gateway
├── discovery-server/      # Eureka Discovery Server
├── config-server/         # Spring Cloud Config Server
├── centralized-logs/      # Directory for aggregated logs
├── docker-compose.yml     # Docker Compose configuration
├── architecture-diagram.png # Architecture diagram image
```



### Key Components
- **API Gateway**: Routes external requests to appropriate microservices, performs load balancing, and enforces security using JWT.
- **User Service**: Handles user registration, authentication, and management.
- **Order Service**: Manages orders and related operations.
- **Payment Service**: Processes payments securely.
- **Service Discovery**: Eureka server for dynamic service registration.
- **Config Server**: Centralized configuration for microservices.
- **Kafka**: Enables asynchronous communication between services.
- **ELK Stack**:
  - **Elasticsearch**: Stores aggregated logs for querying.
  - **Logstash**: Processes and forwards logs to Elasticsearch.
  - **Kibana**: Visualizes logs and provides analytics dashboards.

---

## Features

### Core Features
1. **Service-Oriented Design**: 
   - Each microservice is independently deployable and scalable.
2. **Dynamic Routing and Load Balancing**:
   - API Gateway dynamically routes requests using Eureka discovery and balances the load.
3. **Distributed Transactions**:
   - Implements the SAGA pattern for transaction consistency across microservices.
4. **Asynchronous Messaging**:
   - Kafka facilitates decoupled and reliable communication.
5. **Centralized Logging and Monitoring**:
   - Aggregates and visualizes logs using the ELK stack.

---

## Technical Stack

### Backend:
- **Java Spring Boot**
  - Spring Data JPA
  - Spring Security
  - Spring Cloud Gateway
  - Spring Cloud Config
  - Eureka Discovery
  - SAGA pattern for distributed transactions

### Messaging:
- **Kafka**

### Logging and Monitoring:
- **ELK Stack**:
  - Elasticsearch
  - Logstash
  - Kibana

### Databases:
- **PostgreSQL** (Relational Database)
- **MongoDB** (NoSQL Database)
- **Redis** (In-Memory Database)

### Containerization and CI/CD:
- **Docker**
- **Jenkins**

---

## Installation and Setup

### Prerequisites
1. Docker and Docker Compose installed on your machine.
2. Java 17 or later installed (if running locally).
3. Maven or Gradle installed for building services.

---

### Steps to Run the Project

1. Clone the repository:
   ```bash
   git clone https://github.com/your-repo/microservices-architecture.git
   cd microservices-architecture
   ```
2. Build the services:
    ```bash
    ./gradlew build
    ```
3. Start all services using Docker Compose:
    ```bash
    docker-compose up -d
    ```
4. Verify services:

- Eureka Server: http://localhost:8761
- Config Server: http://localhost:8888
- Elasticsearch: http://localhost:9200
- Kibana: http://localhost:5601

## Viewing Logs in Kibana

Access Kibana:

1. Open your web browser and navigate to http://localhost:5601 (or the URL where Kibana is hosted).

2. Navigate to Logs Section:
    - In the left sidebar, click on Observability.
    - Under the Logs section, click on Stream.

3. Ensure Index Pattern is Configured:
    - Make sure your logs are indexed in Elasticsearch with a consistent pattern like logs-*.
    - If not already set, go to Stack Management > Index Patterns and create an index pattern for logs-* or the specific index name used in your setup.

4. Filter Log Data:
    - Use the search bar at the top to filter logs. For example:
        - To filter by service: appname:user-service or appname:api-gateway.
        - To filter by log level: level:INFO, level:WARN, or level:ERROR.


![Kibana](https://github.com/howk829/spring-microservices-demo/blob/main/kibana.png?raw=true)

---

## API Endpoints (Some of them still under development)

### **User Service**
- `POST /users/register`: Register a new user.
- `POST /users/login`: Log in and receive a JWT token.
- `GET /users`: Retrieve paginated and filtered user data.

### **Order Service**
- `POST /orders`: Create a new order.
- `GET /orders`: Retrieve paginated order data.

### **Payment Service**
- `POST /payments`: Process a payment for an order.

### **API Gateway**
- Acts as the single entry point for external clients.
- Routes all requests to the appropriate microservices.

---

