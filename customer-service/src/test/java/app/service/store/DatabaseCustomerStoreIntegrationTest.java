package app.service.store;

import java.util.List;

import app.service.Application;
import app.service.domain.Transaction;
import app.service.service.TransactionService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = {
    Application.class,
    DatabaseCustomerStoreIntegrationTest.Configuration.class
})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class DatabaseCustomerStoreIntegrationTest {

    @AfterEach
    void cleanUp(@Autowired CustomerRepository customerRepository) {
        customerRepository.deleteAll();
    }

    @Test
    @Order(1)
    void shouldCreateCustomer(@Autowired CustomerStore customerStore) {
        // given & when
        customerStore.createCustomer(new CustomerStore.CreateCustomerParams("John"));

        // then
        final var result = customerStore.getCustomers();
        assertThat(result).hasSize(1).singleElement().satisfies(customer -> {
            assertThat(customer.getName()).isEqualTo("John");
        });
    }

    @Test
    @Order(2)
    void shouldGetCustomers(@Autowired CustomerStore customerStore) {
        // given
        customerStore.createCustomer(new CustomerStore.CreateCustomerParams("John"));
        customerStore.createCustomer(new CustomerStore.CreateCustomerParams("Jane"));
        customerStore.createCustomer(new CustomerStore.CreateCustomerParams("Joe"));

        // when
        final var result = customerStore.getCustomers();

        // then
        assertThat(result).hasSize(3).satisfiesExactly(customer -> {
            assertThat(customer.getName()).isEqualTo("John");
        }, customer -> {
            assertThat(customer.getName()).isEqualTo("Jane");
        }, customer -> {
            assertThat(customer.getName()).isEqualTo("Joe");
        });
    }

    @TestConfiguration
    static class Configuration {
        @Bean
        TransactionService transactionService() {
            return new TransactionService() {
                @Override
                public List<Transaction> getTransactionsByCustomerId(int customerId) {
                    return List.of();
                }
            };
        }
    }
}
