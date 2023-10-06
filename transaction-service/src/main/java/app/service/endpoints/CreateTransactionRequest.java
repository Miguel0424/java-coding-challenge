package app.service.endpoints;

import java.time.Instant;

import lombok.Value;
import org.springframework.lang.NonNull;

@Value
class CreateTransactionRequest {
    @NonNull
    Integer customerId;
    @NonNull
    Double amount;
    @NonNull
    Instant createdAt;
}
