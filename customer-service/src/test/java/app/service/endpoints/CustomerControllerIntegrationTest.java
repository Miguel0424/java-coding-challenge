package app.service.endpoints;

import java.util.List;

import app.service.Application;
import app.service.domain.Transaction;
import app.service.service.TransactionService;
import app.service.store.CustomerStore;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = {
    Application.class,
    CustomerControllerIntegrationTest.Configuration.class
})
@AutoConfigureMockMvc
class CustomerControllerIntegrationTest {

    private static final String CUSTOMERS_URL = "/api/customers";

    @Test
    void shouldGetCustomers(@Autowired MockMvc mockMvc,
                            @Autowired CustomerStore customerStore,
                            @Autowired ObjectMapper objectMapper) throws Exception {
        // given
        customerStore.createCustomer(new CustomerStore.CreateCustomerParams("John"));
        customerStore.createCustomer(new CustomerStore.CreateCustomerParams("Jane"));
        customerStore.createCustomer(new CustomerStore.CreateCustomerParams("Joe"));

        // when
        final var response = mockMvc.perform(get(CUSTOMERS_URL))
            .andExpect(status().isOk())
            .andReturn();

        // then
        final var result = objectMapper.readValue(response.getResponse().getContentAsString(), GetCustomersResponse.class);
        assertThat(result.getCustomers()).hasSize(3).satisfiesExactly(customer -> {
            assertThat(customer.getId()).isEqualTo(1);
            assertThat(customer.getName()).isEqualTo("John");
        }, customer -> {
            assertThat(customer.getId()).isEqualTo(2);
            assertThat(customer.getName()).isEqualTo("Jane");
        }, customer -> {
            assertThat(customer.getId()).isEqualTo(3);
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
