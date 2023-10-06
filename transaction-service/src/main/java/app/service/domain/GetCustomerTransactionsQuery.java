package app.service.domain;

import java.util.List;

import app.service.store.TransactionStore;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@RequiredArgsConstructor
public class GetCustomerTransactionsQuery {

    private final TransactionStore transactionStore;

    public Output execute(Input input) {
        return new Output(transactionStore.getTransactionsByCustomerId(input.getCustomerId()));
    }

    @Value
    public static class Input {
        int customerId;
    }

    @Value
    public class Output {
        List<Transaction> transactions;
    }
}
