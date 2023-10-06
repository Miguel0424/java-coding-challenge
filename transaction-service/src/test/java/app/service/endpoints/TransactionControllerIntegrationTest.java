package app.service.endpoints;

import app.service.Application;
import app.service.error.TransactionNotFoundException;
import app.service.store.TransactionRepository;
import app.service.store.TransactionStore;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
public class TransactionControllerIntegrationTest {

    private static final String TRANSACTIONS_URL = "/api/transactions";
    private static final String GET_TRANSACTION_URL = "/api/transactions/{transactionId}";

    @Test
    void shouldGetTransaction(@Autowired MockMvc mockMvc,
                              @Autowired TransactionStore transactionStore,
                              @Autowired ObjectMapper objectMapper) throws Exception {
        // given
        transactionStore.createTransaction(new TransactionStore.CreateTransactionParams(1, 100.0));

        // when
        final var response = mockMvc.perform(get(GET_TRANSACTION_URL, 1))
            .andExpect(status().isOk())
            .andReturn();

        // then
        final var result = objectMapper.readValue(response.getResponse().getContentAsString(), GetTransactionResponse.class);
        assertThat(result.getCustomerId()).isEqualTo(1);
        assertThat(result.getCreatedAt()).isNotNull();
        assertThat(result.getAmount()).isEqualTo(100.0);
    }

    @Test
    void shouldGetTransactions(@Autowired MockMvc mockMvc,
                               @Autowired TransactionStore transactionStore,
                               @Autowired ObjectMapper objectMapper) throws Exception {
        // given
        final var customerId = 1;
        final var anotherCustomerId = 2;
        transactionStore.createTransaction(new TransactionStore.CreateTransactionParams(customerId, 100.0));
        transactionStore.createTransaction(new TransactionStore.CreateTransactionParams(anotherCustomerId, 100.0));

        // when
        final var response = mockMvc.perform(get(TRANSACTIONS_URL + "?customerId=" + customerId))
            .andExpect(status().isOk())
            .andReturn();

        // then
        final var result = objectMapper.readValue(response.getResponse().getContentAsString(), GetTransactionsResponse.class);
        assertThat(result.getTransactions()).hasSize(1).singleElement().satisfies(transaction -> {
            assertThat(transaction.getCustomerId()).isEqualTo(1);
            assertThat(transaction.getCreatedAt()).isNotNull();
            assertThat(transaction.getAmount()).isEqualTo(100.0);
        });
    }

    @Test
    void shouldCreateTransaction(@Autowired MockMvc mockMvc,
                                 @Autowired TransactionStore transactionStore,
                                 @Autowired TransactionRepository transactionRepository,
                                 @Autowired ObjectMapper objectMapper) throws Exception, TransactionNotFoundException {
        // given
        final var customerId = 1;
        final var amount = 100.0;
        final var request = new CreateTransactionRequest(customerId, amount);

        // when
        final var response = mockMvc.perform(post(TRANSACTIONS_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isCreated())
            .andReturn();

        // then
        final var result = objectMapper.readValue(response.getResponse().getContentAsString(), CreateTransactionResponse.class);
        final var createdTransaction = transactionStore.getTransactionById(result.getId());
        assertThat(createdTransaction.getCustomerId()).isEqualTo(customerId);
        assertThat(createdTransaction.getCreatedAt()).isNotNull();
        assertThat(createdTransaction.getAmount()).isEqualTo(amount);
    }
}
