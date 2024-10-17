# Microservices Project
### Author: Aliaksandr Kolos

This project consists of two microservices: `resource-service` and `song-service`, each with its own PostgreSQL database.
Both services are containerized and can be run using Docker.

## Prerequisites

Make sure you have the following installed:

- Docker
- JDK 21+
- Gradle

## Project Structure

- `resource-service`: Manages resources.
- `song-service`: Manages songs.

Each service has its own PostgreSQL database running in a separate Docker container.

## Running the Application

### Ports

- **8080** - resource-service API
- **7001** - Docker container with resources database
- **8081** - song-service API
- **7002** - Docker container with songs database

### Start `resource-service`

docker run --name resources-db -p 7001:5432 -e POSTGRES_PASSWORD=root -e POSTGRES_DB=resources -d postgres
./gradlew.bat resource-service:bootRun


# Start `song-service` Database

docker run --name songs-db -p 7002:5432 -e POSTGRES_PASSWORD=root -e POSTGRES_DB=songs -d postgres
./gradlew.bat song-service:bootRun


## Docker containers

# Stopping the Docker Containers

* `docker stop resources-db`
* `docker stop songs-db`

# To remove the containers:

* `docker rm resources-db`
* `docker rm songs-db`







