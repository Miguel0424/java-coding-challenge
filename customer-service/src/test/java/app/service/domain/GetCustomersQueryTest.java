package app.service.domain;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import app.service.domain.Customer;
import app.service.domain.GetCustomersQuery;
import app.service.domain.Transaction;
import app.service.service.TransactionService;
import app.service.store.CustomerStore;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class GetCustomersQueryTest {

    @Test
    void shouldGetCustomers() {
        // given
        final var customerStore = new TestCustomerStore();
        final var transactionService = new TestTransactionService();
        final var query = new GetCustomersQuery(customerStore, transactionService);

        // when
        final var result = query.execute();

        // then
        assertThat(result.getCustomers()).hasSize(1).singleElement().satisfies(customer -> {
            assertThat(customer.getId()).isEqualTo(1);
            assertThat(customer.getName()).isEqualTo("John");
            assertThat(customer.getTotalRewardPoints()).isEqualTo(320);
            assertThat(customer.getRewardPointsPerMonths()).hasSize(3).satisfiesExactly(rewardPointsPerMonth -> {
                assertThat(rewardPointsPerMonth.getRewardPoints()).isEqualTo(70);
            }, rewardPointsPerMonth -> {
                assertThat(rewardPointsPerMonth.getRewardPoints()).isEqualTo(10);
            }, rewardPointsPerMonth -> {
                assertThat(rewardPointsPerMonth.getRewardPoints()).isEqualTo(240);
            });
        });
    }

    private static class TestCustomerStore implements CustomerStore {
        @Override
        public Customer createCustomer(CreateCustomerParams params) {
            return null;
        }

        @Override
        public List<Customer> getCustomers() {
            return List.of(
                new Customer(1, "John", null, null)
            );
        }
    }

    private static class TestTransactionService implements TransactionService {
        @Override
        public List<Transaction> getTransactionsByCustomerId(int customerId) {
            return List.of(
                new Transaction(1, 1, getFourMonthsAgo(), 100.0),
                new Transaction(2, 1, getFourMonthsAgo(), 80.0),
                new Transaction(3, 1, getFourMonthsAgo(), 150.0),
                new Transaction(4, 1, getEightyDaysAgo(), 20.0),
                new Transaction(5, 1, getEightyDaysAgo(), 80.0),
                new Transaction(6, 1, getEightyDaysAgo(), 90.0),
                new Transaction(7, 1, getFiftyDaysAgo(), 10.0),
                new Transaction(8, 1, getFiftyDaysAgo(), 60.0),
                new Transaction(9, 1, getTwentyAgo(), 60.0),
                new Transaction(10, 1, getTwentyAgo(), 190.0)
            );
        }

        private Instant getFourMonthsAgo() {
            return Instant.now().minus(120, ChronoUnit.DAYS);
        }

        private Instant getEightyDaysAgo() {
            return Instant.now().minus(80, ChronoUnit.DAYS);
        }

        private Instant getFiftyDaysAgo() {
            return Instant.now().minus(50, ChronoUnit.DAYS);
        }

        private Instant getTwentyAgo() {
            return Instant.now().minus(20, ChronoUnit.DAYS);
        }
    }
}
