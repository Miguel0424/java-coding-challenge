package app.service.domain;

import java.time.Month;

import lombok.Value;

@Value
public class RewardPointsPerMonth {
    Double rewardPoints;
    Month month;
}