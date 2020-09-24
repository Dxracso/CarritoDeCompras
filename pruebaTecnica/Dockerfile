FROM openjdk:8-jdk-alpine
ARG PROPIEDADES_FILE=target/application.properties
COPY ${PROPIEDADES_FILE} application.properties
ARG JAR_FILE=target/pruebaTecnica-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} pruebaTecnica-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar", "-Dserver.port=8080","-Dspring.config.location=application.properties","pruebaTecnica-0.0.1-SNAPSHOT.jar"]
