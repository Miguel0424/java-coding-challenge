package app.service.service;

import java.util.List;

import app.service.domain.Transaction;
import lombok.Value;

public interface TransactionService {
    List<Transaction> getTransactionsByCustomerId(int customerId);

    @Value
    class TransactionsResponse {
        List<Transaction> transactions;
    }
}
