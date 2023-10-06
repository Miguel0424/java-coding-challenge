package app.service.error;

public class CustomerNotFoundException extends BaseException {
    public CustomerNotFoundException(int customerId) {
        super("CUSTOMER_NOT_FOUND", "Customer with id " + customerId + " was not found");
    }
}
