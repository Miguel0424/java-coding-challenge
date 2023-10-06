package app.service.endpoints;

import lombok.Value;

@Value
class CreateTransactionRequest {
    Integer customerId;
    Double amount;
}
