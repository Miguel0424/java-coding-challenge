package app.service.domain;

import java.util.List;

import lombok.Value;
import lombok.With;

@Value
public class Customer {
    Integer id;
    String name;
    @With
    List<RewardPointsPerMonth> rewardPointsPerMonths;
    @With
    Double totalRewardPoints;
}
