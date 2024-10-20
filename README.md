# Microservices Project

### Author: Aliaksandr Kolos

This project consists of two microservices: `resource-service` and `song-service`, each with its own PostgreSQL
database. Both services are containerized and can be run using Docker.

## Prerequisites

Make sure you have the following installed:

- Docker
- JDK 21+
- Gradle

## Project Structure

- `resource-service`: Manages resources.
- `song-service`: Manages songs.

Each service has its own PostgreSQL database running in a separate Docker container.

## Running the Application with Docker Compose

To start the application using Docker Compose, follow these steps:

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

## Running the Application Manually

## Setup

1. Build the Docker image for both services using Gradle:
    ```bash
    ./gradlew jibDockerBuild
    ```

2. Create a Docker network:
    ```bash
    docker network create my-network
    ```

### Start `resource-service`

```bash
    docker run --name resource-service-db --network my-network -p 7001:5432 -e POSTGRES_PASSWORD=root -e POSTGRES_DB=resources -d postgres
    docker run --name resource-service --network my-network -p 8080:8080 -d resource-app-image
```

### Start `song-service` Database

```bash
    docker run --name song-service-db  --network my-network -p 7002:5432 -e POSTGRES_PASSWORD=root -e POSTGRES_DB=songs -d postgres
    docker run --name song-service --network my-network -p 8081:8080 -d song-app-image
```

# Docker containers

### Stopping the Docker Containers

* `docker stop resource-service-db`
* `docker stop song-service-db`
* `docker stop resource-service`
* `docker stop song-service`

### To remove the containers:

* `docker rm resource-service-db`
* `docker rm song-service-db`
* `docker rm resource-service`
* `docker rm song-service`

