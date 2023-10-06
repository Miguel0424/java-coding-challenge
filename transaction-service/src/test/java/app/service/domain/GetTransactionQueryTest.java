package app.service.domain;

import java.time.Instant;
import java.util.List;

import app.service.error.TransactionNotFoundException;
import app.service.store.TransactionStore;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class GetTransactionQueryTest {

    @Test
    void shouldGetTransaction() throws TransactionNotFoundException {
        // given
        final var transactionStore = new TestTransactionStore();
        final var query = new GetTransactionQuery(transactionStore);

        // when
        final var output = query.execute(new GetTransactionQuery.Input(1));

        // then
        assertThat(output.getTransaction().getId()).isEqualTo(1);
        assertThat(output.getTransaction().getCustomerId()).isEqualTo(1);
        assertThat(output.getTransaction().getCreatedAt()).isNotNull();
        assertThat(output.getTransaction().getAmount()).isEqualTo(100.0);
    }

    private static class TestTransactionStore implements TransactionStore {

        @Override
        public Transaction getTransactionById(int id) {
            return new Transaction(id, 1, Instant.now(), 100.0);
        }

        @Override
        public List<Transaction> getTransactionsByCustomerId(int customerId) {
            return null;
        }

        @Override
        public Transaction createTransaction(CreateTransactionParams params) {
            return null;
        }
    }
}
