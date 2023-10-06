package app.service.model;

import java.time.Instant;

import app.service.domain.Transaction;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table
@Entity
public class TransactionEntity {
    @Id
    @GeneratedValue
    Integer id;
    Integer customerId;
    Instant createdAt;
    Double amount;

    public Transaction toDomain() {
        return new Transaction(id, customerId, createdAt, amount);
    }
}
