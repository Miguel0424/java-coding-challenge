package app.service.model;

import app.service.domain.Customer;
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
public class CustomerEntity {
    @Id
    @GeneratedValue
    Integer id;

    String name;

    public Customer toDomain() {
        return new Customer(id, name, null, null);
    }
}
