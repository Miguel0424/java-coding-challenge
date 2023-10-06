package app.service.domain;

import java.time.Instant;
import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import app.service.service.TransactionService;
import app.service.store.CustomerStore;
import lombok.Value;

public class GetCustomersQuery {

    private final CustomerStore customerStore;
    private final TransactionService transactionService;
    private final RewardPointsCalculator rewardPointsCalculator;

    public GetCustomersQuery(CustomerStore customerStore, TransactionService transactionService) {
        this.customerStore = customerStore;
        this.transactionService = transactionService;
        this.rewardPointsCalculator = new RewardPointsCalculator();
    }

    public Output execute() {
        final var results = customerStore.getCustomers().stream()
            .map(customer -> Optional.of(transactionService.getTransactionsByCustomerId(customer.getId()))
                .map(this::removeOlderThanThreeMonths)
                .flatMap(transactions -> Optional.of(customer.withTotalRewardPoints(rewardPointsCalculator.calculateTotalRewardPoints(transactions)))
                    .map(customerWithTotalRewardPoints -> customerWithTotalRewardPoints.withRewardPointsPerMonths(calculateRewardPointsOfPreviousThreeMonths(transactions)))).get())
            .toList();
        return Output.of(results);
    }

    private List<Transaction> removeOlderThanThreeMonths(List<Transaction> transactions) {
        final var threeMonthsAgo = Instant.now().minus(90, ChronoUnit.DAYS);
        return transactions.stream()
            .filter(transaction -> transaction.getCreatedAt().isAfter(threeMonthsAgo))
            .toList();
    }

    private List<RewardPointsPerMonth> calculateRewardPointsOfPreviousThreeMonths(List<Transaction> transactions) {
        final var threeMonthsAgo = Instant.now().minus(90, ChronoUnit.DAYS);
        final var twoMonthsAgo = Instant.now().minus(60, ChronoUnit.DAYS);
        final var oneMonthAgo = Instant.now().minus(30, ChronoUnit.DAYS);
        final var transactionsOfFirstMonth = transactions.stream()
            .filter(transaction -> transaction.getCreatedAt().isAfter(threeMonthsAgo) && transaction.getCreatedAt().isBefore(twoMonthsAgo))
            .toList();
        final var transactionsOfSecondMonth = transactions.stream()
            .filter(transaction -> transaction.getCreatedAt().isAfter(twoMonthsAgo) && transaction.getCreatedAt().isBefore(oneMonthAgo))
            .toList();
        final var transactionsOfThirdMonth = transactions.stream()
            .filter(transaction -> transaction.getCreatedAt().isAfter(oneMonthAgo))
            .toList();
        return List.of(
            new RewardPointsPerMonth(rewardPointsCalculator.calculateTotalRewardPoints(transactionsOfFirstMonth), getMonthOfInstant(threeMonthsAgo)),
            new RewardPointsPerMonth(rewardPointsCalculator.calculateTotalRewardPoints(transactionsOfSecondMonth), getMonthOfInstant(twoMonthsAgo)),
            new RewardPointsPerMonth(rewardPointsCalculator.calculateTotalRewardPoints(transactionsOfThirdMonth), getMonthOfInstant(oneMonthAgo))
        );
    }

    private Month getMonthOfInstant(Instant instant) {
        return LocalDate.ofInstant(instant, ZoneId.systemDefault()).getMonth();
    }

    @Value(staticConstructor = "of")
    public static class Output {
        List<Customer> customers;
    }
}
