package app.service.domain;

import java.time.Instant;
import java.util.List;

import app.service.store.TransactionStore;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CreateTransactionCommandTest {

    @Test
    void shouldCreateTransaction() {
        // given
        final var customerId = 1;
        final var amount = 100.0;
        final var transactionStore = new TestTransactionStore();
        final var command = new CreateTransactionCommand(transactionStore);

        // when
        final var output = command.execute(new CreateTransactionCommand.Input(customerId, amount, Instant.now()));

        // then
        assertThat(output.getId()).isEqualTo(1);
    }

    private static class TestTransactionStore implements TransactionStore {

        @Override
        public Transaction getTransactionById(int id) {
            return null;
        }

        @Override
        public List<Transaction> getTransactionsByCustomerId(int customerId) {
            return null;
        }

        @Override
        public Transaction createTransaction(CreateTransactionParams params) {
            return new Transaction(1, params.getCustomerId(), params.getCreatedAt(), params.getAmount());
        }
    }
}
