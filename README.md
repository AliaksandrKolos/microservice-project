# Microservices Project

### Author: Aliaksandr Kolos

This project consists of several microservices, each with its own specific functionality:

- `discovery-server`: Service discovery using Eureka, allowing other services to find each other.
- `resource-service`: Manages resources, connected to its own PostgreSQL database.
- `song-service`: Manages songs, also with a separate PostgreSQL database.
- `authorization-service`: Handles user authorization and authentication with its own PostgreSQL database.
- `resource-processor`: Processes resources for additional functionality.
- `gateway-service`: Serves as the API gateway, routing requests to other services.
- `message-broker`: RabbitMQ message broker for inter-service communication.
- `localstack`: Local AWS emulator for testing purposes.
- `prometheus`: Monitoring setup with Prometheus for tracking system performance.

All services are containerized and can be run using Docker.


## Prerequisites

Make sure you have the following installed:

- Docker
- JDK 21+
- Gradle

## Project Structure

- `resource-service`: Manages resources.
- `song-service`: Manages songs.
- `discovery-server`: Service discovery.
- `authorization-service`: Handles user authorization and authentication.
- `resource-processor`: Processes resources.
- `gateway-service`: Serves as the API gateway.
- `message-broker`: RabbitMQ message broker for inter-service communication.
- `localstack`: Local AWS emulator for testing purposes.
- `prometheus`: Monitoring setup with Prometheus.


## Services Overview

| Service Name              | Port(s)            | Description                                |
|---------------------------|--------------------|--------------------------------------------|
| `discovery-server`        | 8761               | Service discovery using Eureka.            |
| `resource-service`        | 8080               | Resource management.                        |
| `song-service`            | 8080               | Song management.                           |
| `authorization-service`   | 8585               | User authorization and authentication.     |
| `gateway-service`         | 7070               | API Gateway for routing requests.          |
| `message-broker`          | 5672, 15672        | RabbitMQ message broker for inter-service communication. |
| `localstack`              | 4566               | Local AWS services emulator for testing.   |
| `prometheus`              | 9090               | Monitoring setup with Prometheus.         |



## Running the Application with Docker Compose

To start the application using Docker Compose, follow these steps:

1. Build the Docker image for both services using Gradle:
 ```bash
 ./gradlew jibDockerBuild
 ```
1. Ensure that you have the `docker-compose.yml` file in your project root directory. This file contains the necessary
   configuration for both services.

2. Start the application using Docker Compose:
    ```bash
    docker-compose up
    ```

3. To run the application in detached mode (background), use:
    ```bash
    docker-compose up -d
    ```

### Stopping the Application

To stop the application, run:

```bash
  docker-compose down
```


