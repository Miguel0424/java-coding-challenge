FROM openjdk:19
LABEL authors="Miguel"

WORKDIR /app

COPY build/libs/transaction-service-1.0-SNAPSHOT.jar .

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "transaction-service-1.0-SNAPSHOT.jar"]