package app.service.configuration;

import app.service.domain.CreateCustomerCommand;
import app.service.domain.GetCustomersQuery;
import app.service.service.RestTransactionService;
import app.service.service.TransactionService;
import app.service.store.CustomerRepository;
import app.service.store.CustomerStore;
import app.service.store.DatabaseCustomerStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class CustomerConfiguration {
    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    CustomerStore customerStore(CustomerRepository customerRepository) {
        return new DatabaseCustomerStore(customerRepository);
    }

    @Bean
    TransactionService transactionService(RestTemplate restTemplate) {
        return new RestTransactionService(restTemplate);
    }

    @Bean
    GetCustomersQuery getCustomersQuery(CustomerStore customerStore, TransactionService transactionService) {
        return new GetCustomersQuery(customerStore, transactionService);
    }

    @Bean
    CreateCustomerCommand createCustomerCommand(CustomerStore customerStore) {
        return new CreateCustomerCommand(customerStore);
    }
}
