package app.service.endpoints;

import java.time.Instant;

import lombok.Value;

@Value
class GetTransactionResponse {
    Integer id;
    Integer customerId;
    Instant createdAt;
    Double amount;
}
