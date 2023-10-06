package app.service.domain;

import app.service.store.CustomerStore;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@RequiredArgsConstructor
public class CreateCustomerCommand {

    private final CustomerStore customerStore;

    public Output execute(Input input) {
        final var customerId = customerStore.createCustomer(new CustomerStore.CreateCustomerParams(input.getName())).getId();
        return new Output(customerId);
    }

    @Value
    public static class Input {
        String name;
    }

    @Value
    public static class Output {
        int id;
    }
}
