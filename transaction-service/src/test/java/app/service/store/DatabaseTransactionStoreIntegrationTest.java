package app.service.store;

import java.time.Instant;

import app.service.Application;
import app.service.domain.Transaction;
import app.service.error.TransactionNotFoundException;
import app.service.model.TransactionEntity;
import app.service.store.TransactionRepository;
import app.service.store.TransactionStore;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = Application.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class DatabaseTransactionStoreIntegrationTest {

    @AfterEach
    void cleanup(@Autowired TransactionRepository transactionRepository) {
        transactionRepository.deleteAll();
    }

    @Test
    @Order(1)
    void shouldGetTransaction(@Autowired TransactionStore transactionStore,
                              @Autowired TransactionRepository transactionRepository) throws TransactionNotFoundException {
        // given
        final var transaction = new TransactionEntity(null, 1, Instant.now(), 100.0);
        transactionRepository.save(transaction);

        // when
        final var output = transactionStore.getTransactionById(1);

        // then
        assertThat(output.getCustomerId()).isEqualTo(1);
        assertThat(output.getCreatedAt()).isNotNull();
        assertThat(output.getAmount()).isEqualTo(100.0);
    }

    @Test
    @Order(2)
    void shouldGetCustomerTransactions(@Autowired TransactionStore transactionStore,
                                       @Autowired TransactionRepository transactionRepository) {
        // given
        final var customerId = 1;
        final var anotherCustomerId = 2;
        final var transaction1 = new TransactionEntity(null, customerId, Instant.now(), 100.0);
        final var transaction2 = new TransactionEntity(null, customerId, Instant.now(), 80.0);
        final var transaction3 = new TransactionEntity(null, anotherCustomerId, Instant.now(), 100.0);
        transactionRepository.save(transaction1);
        transactionRepository.save(transaction2);
        transactionRepository.save(transaction3);

        // when
        final var output = transactionStore.getTransactionsByCustomerId(customerId);

        // then
        assertThat(output).hasSize(2).satisfiesExactly(transaction -> {
            assertThat(transaction.getCustomerId()).isEqualTo(customerId);
            assertThat(transaction.getAmount()).isEqualTo(100.0);
        }, transaction -> {
            assertThat(transaction.getCustomerId()).isEqualTo(customerId);
            assertThat(transaction.getAmount()).isEqualTo(80.0);
        });
    }

    @Test
    @Order(3)
    void shouldCreateTransaction(@Autowired TransactionStore transactionStore) {
        // given
        final var customerId = 1;
        final var amount = 100.0;

        // when
        final var output = transactionStore.createTransaction(new TransactionStore.CreateTransactionParams(customerId, amount));

        // then
        assertThat(output.getId()).isEqualTo(5);
    }
}
