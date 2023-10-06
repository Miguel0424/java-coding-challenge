package app.service.store;

import java.time.Instant;
import java.util.List;

import app.service.domain.Transaction;
import app.service.error.TransactionNotFoundException;
import app.service.model.TransactionEntity;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DatabaseTransactionStore implements TransactionStore {

    private final TransactionRepository transactionRepository;

    @Override
    public Transaction getTransactionById(int id) throws TransactionNotFoundException {
        return transactionRepository.findById(id)
            .map(TransactionEntity::toDomain)
            .orElseThrow(() -> new TransactionNotFoundException(id));
    }

    @Override
    public List<Transaction> getTransactionsByCustomerId(int customerId) {
        return transactionRepository.findByCustomerId(customerId).stream()
            .map(TransactionEntity::toDomain)
            .toList();
    }

    @Override
    public Transaction createTransaction(CreateTransactionParams params) {
        return transactionRepository.save(new TransactionEntity(
            null,
            params.getCustomerId(),
            Instant.now(),
            params.getAmount()
        )).toDomain();
    }
}
