FROM openjdk:8-jdk-alpine
ARG PROPIEDADES_FILE=target/application.properties
COPY ${PROPIEDADES_FILE} application.properties
ARG JAR_FILE=target/*.jar
# COPY geoip_city.mmdb geoip_city.mmdb
# COPY geoip_country.mmdb geoip_country.mmdb
COPY ${JAR_FILE} sesiones.jar
ENTRYPOINT ["java","-jar", "-Dserver.port=8080","-Dspring.config.location=application.properties","/sesiones.jar"]
