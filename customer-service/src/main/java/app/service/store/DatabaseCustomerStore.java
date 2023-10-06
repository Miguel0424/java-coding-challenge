package app.service.store;

import java.util.List;

import app.service.model.CustomerEntity;
import app.service.domain.Customer;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DatabaseCustomerStore implements CustomerStore {

    private final CustomerRepository customerRepository;

    @Override
    public Customer createCustomer(CreateCustomerParams params) {
        return customerRepository.save(new CustomerEntity(null, params.getName()))
            .toDomain();
    }

    public List<Customer> getCustomers() {
        return customerRepository.findAll().stream()
            .map(CustomerEntity::toDomain)
            .toList();
    }
}
