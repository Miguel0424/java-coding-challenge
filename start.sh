#!/bin/sh

cd customer-service
gradle clean && gradle bootJar

cd ..

cd transaction-service
gradle clean && gradle bootJar

cd ..
docker-compose up -d