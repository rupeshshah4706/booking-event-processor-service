# Ticket Booking Platform
## booking-event-processor

A Spring Boot service for processing booking events. Built with Java, Maven, and Docker.

## Features

- Processes booking-related events
- RESTful API endpoints
- Easily deployable with Docker
- CI/CD pipeline with GitHub Actions

## Prerequisites

- Java 17+
- Maven 3.8+
- Docker (for containerization)
- Access to PostgreSQL, Redis, Kafka, and ZooKeeper (locally or via Kubernetes)

## Overview
## Getting Started
1. **Clone the Repository**
   ```sh
   git clone
    cd booking-platform-infra
    ```
2. **Build the Project**
3. ```sh
   mvn clean install
   ```
4. **Run the Application**
   ```sh
    mvn spring-boot:run
    ```
5. **Access the Service**
6. Open your browser and navigate to `http://localhost:8080/api/events` to access the event processing APIs.
7. **Dockerize the Application**
   ```sh
   docker build -t booking-event-processor .
   docker run -p 8080:8080 booking-event-processor
   ```
8. **Deploy to Kubernetes**
9. Apply the Kubernetes manifests:
   ```sh
   kubectl apply -f k8s/booking-event-processor/deployment.yaml
   kubectl apply -f k8s/booking-event-processor/service.yaml
   ```
   