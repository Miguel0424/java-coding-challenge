package app.service.endpoints;

import java.util.List;

import app.service.domain.Customer;
import lombok.Value;

@Value
class GetCustomersResponse {
    List<Customer> customers;
}
