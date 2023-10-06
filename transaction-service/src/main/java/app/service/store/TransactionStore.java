package app.service.store;

import java.time.Instant;
import java.util.List;

import app.service.domain.Transaction;
import app.service.error.TransactionNotFoundException;
import lombok.Value;

public interface TransactionStore {
    Transaction getTransactionById(int id) throws TransactionNotFoundException;

    List<Transaction> getTransactionsByCustomerId(int customerId);

    Transaction createTransaction(CreateTransactionParams params);

    @Value
    class CreateTransactionParams {
        int customerId;
        double amount;
        Instant createdAt;
    }
}
