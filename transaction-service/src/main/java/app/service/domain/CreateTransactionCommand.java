package app.service.domain;

import java.time.Instant;

import app.service.store.TransactionStore;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@RequiredArgsConstructor
public class CreateTransactionCommand {

    private final TransactionStore transactionStore;

    public Output execute(Input input) {
        return new Output(transactionStore.createTransaction(new TransactionStore.CreateTransactionParams(
                input.getCustomerId(),
                input.getAmount(),
                input.getCreatedAt()
            ))
            .getId()
        );
    }

    @Value
    public static class Input {
        int customerId;
        double amount;
        Instant createdAt;
    }

    @Value
    public static class Output {
        int id;
    }
}
