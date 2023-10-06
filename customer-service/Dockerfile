FROM openjdk:19
LABEL authors="Miguel"

WORKDIR /app

COPY build/libs/customer-service-1.0-SNAPSHOT.jar .

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "customer-service-1.0-SNAPSHOT.jar"]