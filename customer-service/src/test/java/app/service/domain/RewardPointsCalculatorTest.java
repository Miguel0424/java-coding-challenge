package app.service.domain;

import java.time.Instant;
import java.util.List;

import app.service.domain.RewardPointsCalculator;
import app.service.domain.Transaction;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RewardPointsCalculatorTest {

    @Test
    void shouldCalculateTotalRewardPointsOfTransactions() {
        // given
        var transactions = new Transaction(1, 1, Instant.now(), 20);
        var transactions2 = new Transaction(2, 1, Instant.now(), 55);
        var transactions3 = new Transaction(3, 1, Instant.now(), 70);
        var transactions4 = new Transaction(4, 1, Instant.now(), 40);
        var transactions5 = new Transaction(5, 1, Instant.now(), 156);
        var calculator = new RewardPointsCalculator();

        // when
        var result = calculator.calculateTotalRewardPoints(List.of(transactions, transactions2, transactions3, transactions4, transactions5));

        // then
        assertThat(result).isEqualTo(187);
    }


}
