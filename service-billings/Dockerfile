FROM openjdk:17-alpine

WORKDIR opt/server
COPY ./target/service-billings-0.0.1-SNAPSHOT.jar server.jar

EXPOSE 8085

ENTRYPOINT ["java", "-jar", "server.jar"]