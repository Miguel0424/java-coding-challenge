package app.service.service;

import java.util.List;

import app.service.domain.Transaction;

public interface TransactionService {
    List<Transaction> getTransactionsByCustomerId(int customerId);
}
