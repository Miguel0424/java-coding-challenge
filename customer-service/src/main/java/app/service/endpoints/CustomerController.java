package app.service.endpoints;

import app.service.domain.CreateCustomerCommand;
import app.service.domain.GetCustomersQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class CustomerController {

    private final GetCustomersQuery getCustomersQuery;
    private final CreateCustomerCommand createCustomerCommand;

    @GetMapping("/api/customers")
    @ResponseStatus(HttpStatus.OK)
    public GetCustomersResponse getCustomers() {
        final var output = getCustomersQuery.execute();
        return new GetCustomersResponse(output.getCustomers());
    }

    @PostMapping("/api/customers")
    @ResponseStatus(HttpStatus.CREATED)
    public CreateCustomerResponse createCustomer(@RequestBody CreateCustomerRequest request) {
        final var output = createCustomerCommand.execute(new CreateCustomerCommand.Input(request.getCustomerName()));
        return new CreateCustomerResponse(output.getId());
    }
}
