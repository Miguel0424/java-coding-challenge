## Instructions

### How to run the applications?
run the `start.sh` command which will clean the builds, then create new Jars for each service. Then it will trigger the docker-compose and run the applications.

### How can the endpoints be used?
The following steps can be done using any client that can do HTTP requests - Browser, Curl or Postman.
1. Create a customer by calling `POST` / `http://localhost:8080/api/customers`
2. Find the customer info by calling `GET` / `http://localhost:8080/api/customers`
3. Create transactions for the customer by calling `POST` / `http://localhost:8081/api/transactions`
4. Get info and transaction summary of customer by calling `GET` / `http://localhost:8080/api/customers`

### Unit and Integration tests
Domain classes with Query and Commands have been tested using Unit Tests. Store and Controllers have been tested using Integration Tests.
You can run the tests of each service by calling `gradle test`

### Health check
In order to check the health of customer-service, Ping/open http://localhost:8080/actuator/health
In order to check the health of transaction-service, Ping/open http://localhost:8081/actuator/health

### Run with Docker
In case you want to run the services using docker without triggering the `start.sh` script, you can enter `docker-compose up` in the terminal and use the services.
