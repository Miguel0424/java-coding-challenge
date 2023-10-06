package app.service.error;

public class TransactionNotFoundException extends BaseException {
    public TransactionNotFoundException(int id) {
        super("TRANSACTION_NOT_FOUND", "Transaction with id " + id + " was not found");
    }
}
