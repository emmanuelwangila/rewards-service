package com.rewards_service.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class RewardEventPublisher {

    private final RabbitTemplate rabbitTemplate;

    public void publishRewardEarned(UUID userId, long points, String description) {
        RewardEvent event = new RewardEvent(userId, "EARNED", points, description);
        rabbitTemplate.convertAndSend("rewards.exchange", "reward.earned", event);
        log.info("Published reward earned event for user {}: {} points", userId, points);
    }

    public void publishRewardRedeemed(UUID userId, long points, String description) {
        RewardEvent event = new RewardEvent(userId, "REDEEMED", points, description);
        rabbitTemplate.convertAndSend("rewards.exchange", "reward.redeemed", event);
        log.info("Published reward redeemed event for user {}: {} points", userId, points);
    }

    public static class RewardEvent {
        private UUID userId;
        private String type;
        private long points;
        private String description;

        public RewardEvent() {}

        public RewardEvent(UUID userId, String type, long points, String description) {
            this.userId = userId;
            this.type = type;
            this.points = points;
            this.description = description;
        }

        // Getters and setters
        public UUID getUserId() { return userId; }
        public void setUserId(UUID userId) { this.userId = userId; }
        public String getType() { return type; }
        public void setType(String type) { this.type = type; }
        public long getPoints() { return points; }
        public void setPoints(long points) { this.points = points; }
        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
    }
}