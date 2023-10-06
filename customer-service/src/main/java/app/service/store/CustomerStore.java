package app.service.store;

import java.util.List;

import app.service.domain.Customer;
import lombok.Value;

public interface CustomerStore {
    Customer createCustomer(CreateCustomerParams params);

    List<Customer> getCustomers();

    @Value
    class CreateCustomerParams {
        String name;
    }
}
