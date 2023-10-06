package app.service.endpoints;

import app.service.domain.CreateTransactionCommand;
import app.service.domain.GetCustomerTransactionsQuery;
import app.service.domain.GetTransactionQuery;
import app.service.error.TransactionNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class TransactionController {

    private final GetTransactionQuery getTransactionQuery;
    private final GetCustomerTransactionsQuery getCustomerTransactionsQuery;
    private final CreateTransactionCommand createTransactionCommand;

    @GetMapping("/api/transactions/{transactionId}")
    @ResponseStatus(HttpStatus.OK)
    public GetTransactionResponse getTransaction(@PathVariable int transactionId) throws TransactionNotFoundException {
        final var output = getTransactionQuery.execute(new GetTransactionQuery.Input(transactionId));
        return new GetTransactionResponse(
            output.getTransaction().getId(),
            output.getTransaction().getCustomerId(),
            output.getTransaction().getCreatedAt(),
            output.getTransaction().getAmount()
        );
    }

    @GetMapping("/api/transactions")
    @ResponseStatus(HttpStatus.OK)
    public GetTransactionsResponse getTransactions(@RequestParam int customerId) {
        final var output = getCustomerTransactionsQuery.execute(new GetCustomerTransactionsQuery.Input(customerId));
        return new GetTransactionsResponse(output.getTransactions());
    }

    @PostMapping("/api/transactions")
    @ResponseStatus(HttpStatus.CREATED)
    public CreateTransactionResponse createTransaction(@RequestBody CreateTransactionRequest createTransactionRequest) {
        final var output = createTransactionCommand.execute(new CreateTransactionCommand.Input(
            createTransactionRequest.getCustomerId(),
            createTransactionRequest.getAmount(),
            createTransactionRequest.getCreatedAt()
        ));
        return new CreateTransactionResponse(output.getId());
    }
}
