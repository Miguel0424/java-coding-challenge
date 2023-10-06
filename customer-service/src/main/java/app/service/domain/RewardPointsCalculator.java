package app.service.domain;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import app.service.domain.Transaction;

public class RewardPointsCalculator {
    public double calculateTotalRewardPoints(List<Transaction> transactions) {
        AtomicReference<Double> total = new AtomicReference<>((double) 0);
        transactions.stream()
            .filter(transaction -> transaction.getAmount() >= 50)
            .peek(transaction -> total.set(total.get() + (transaction.getAmount() - 50)))
            .filter(transaction -> transaction.getAmount() >= 100)
            .peek(transaction -> total.set(total.get() + (transaction.getAmount() - 100)))
            .collect(Collectors.toSet());
        return total.get();
    }
}
