package app.service.domain;

import app.service.error.TransactionNotFoundException;
import app.service.store.TransactionStore;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@RequiredArgsConstructor
public class GetTransactionQuery {

    private final TransactionStore transactionStore;

    public Output execute(Input input) throws TransactionNotFoundException {
        return new Output(transactionStore.getTransactionById(input.getId()));
    }

    @Value
    public static class Input {
        int id;
    }

    @Value
    public static class Output {
        Transaction transaction;
    }
}
