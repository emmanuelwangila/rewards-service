package com.rewards_service;

import com.rewards_service.application.service.RewardService;
import com.rewards_service.application.service.UserService;
import com.rewards_service.domain.model.UserTier;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class RewardServiceIntegrationTest {

    @Autowired
    private RewardService rewardService;

    @Autowired
    private UserService userService;

    @Test
    void testAwardAndRedeemPoints() {
        // Create user
        var user = userService.createUser("ext123", "John Doe", "john@example.com", UserTier.STANDARD);

        // Award points
        rewardService.awardPoints(user.getId(), 10000, "test-ref"); // $100

        // Check balance
        var balance = rewardService.getBalance(user.getId());
        assertThat(balance.getPoints()).isEqualTo(100); // 100 points

        // Redeem points
        rewardService.redeemPoints(user.getId(), 50);

        // Check balance again
        balance = rewardService.getBalance(user.getId());
        assertThat(balance.getPoints()).isEqualTo(50);
        assertThat(balance.getBalance()).isEqualTo(0.5); // $0.50
    }
}