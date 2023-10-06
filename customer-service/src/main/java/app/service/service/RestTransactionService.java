package app.service.service;

import java.util.List;

import app.service.domain.Transaction;
import lombok.RequiredArgsConstructor;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
public class RestTransactionService implements TransactionService {

    private final RestTemplate restTemplate;

    public List<Transaction> getTransactionsByCustomerId(int customerId) {
        return restTemplate.getForObject("http://localhost:8080/api/transactions?customerId=" + customerId, List.class);
    }
}
