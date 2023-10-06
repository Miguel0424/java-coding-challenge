package app.service.endpoints;

import java.util.List;

import app.service.domain.Transaction;
import lombok.Value;

@Value
class GetTransactionsResponse {
    List<Transaction> transactions;
}
