package app.service.error;

public class TransactionsNotFoundException extends BaseException {
    public TransactionsNotFoundException(int customerId) {
        super("TRANSACTIONS_NOT_FOUND", "Transactions for customer with id " + customerId + " was not found");
    }
}
