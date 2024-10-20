FROM gradle:8.10.2-jdk21-alpine AS build
COPY build.gradle /build/
WORKDIR /build/
COPY src /build/src/
RUN gradle build --no-daemon -x test

#Run stage
FROM amazoncorretto:21-alpine
ARG JAR_FILE=build/build/libs/app.jar
COPY --from=build ${JAR_FILE} /opt/song-service/song.jar
ENTRYPOINT ["java", "-jar", "/opt/song-service/song.jar"]