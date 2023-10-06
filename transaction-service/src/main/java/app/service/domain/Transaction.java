package app.service.domain;

import java.time.Instant;

import lombok.Value;

@Value
public class Transaction {
    Integer id;
    Integer customerId;
    Instant createdAt;
    Double amount;
}
