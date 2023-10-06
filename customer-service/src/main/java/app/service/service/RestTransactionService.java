package app.service.service;

import java.util.List;

import app.service.domain.Transaction;
import lombok.RequiredArgsConstructor;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
public class RestTransactionService implements TransactionService {

    private final RestTemplate restTemplate;

    public List<Transaction> getTransactionsByCustomerId(int customerId) {
        try {
            return restTemplate.getForObject("http://transaction-service:8081/api/transactions?customerId=" + customerId, TransactionsResponse.class).getTransactions();
        } catch (Exception e) {
            return List.of();
        }
    }
}
