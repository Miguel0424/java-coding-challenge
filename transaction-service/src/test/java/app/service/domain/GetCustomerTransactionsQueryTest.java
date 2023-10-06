package app.service.domain;

import java.time.Instant;
import java.util.List;

import app.service.error.TransactionNotFoundException;
import app.service.store.TransactionStore;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class GetCustomerTransactionsQueryTest {

    @Test
    void shouldGetCustomerTransactions() {
        // given
        final var customerId = 1;
        final var transactionStore = new TestTransactionStore();
        final var query = new GetCustomerTransactionsQuery(transactionStore);

        // when
        final var output = query.execute(new GetCustomerTransactionsQuery.Input(customerId));

        // then
        assertThat(output.getTransactions()).hasSize(1).satisfiesExactly(transaction -> {
            assertThat(transaction.getId()).isEqualTo(1);
            assertThat(transaction.getCustomerId()).isEqualTo(customerId);
            assertThat(transaction.getAmount()).isEqualTo(100.0);
        });
    }

    private static class TestTransactionStore implements TransactionStore {

        @Override
        public Transaction getTransactionById(int id) throws TransactionNotFoundException {
            return null;
        }

        @Override
        public List<Transaction> getTransactionsByCustomerId(int customerId) {
            return List.of(new Transaction(1, customerId, Instant.now(), 100.0));
        }

        @Override
        public Transaction createTransaction(CreateTransactionParams params) {
            return null;
        }

    }
}
