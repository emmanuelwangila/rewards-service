package com.rewards_service.dto;

import java.util.UUID;

public class AwardResponse {

    private UUID userId;
    private long pointsAwarded;
    private UUID transactionId;

    public AwardResponse(UUID userId, long pointsAwarded, UUID transactionId) {
        this.userId = userId;
        this.pointsAwarded = pointsAwarded;
        this.transactionId = transactionId;
    }

    // Getters
    public UUID getUserId() {
        return userId;
    }

    public long getPointsAwarded() {
        return pointsAwarded;
    }

    public UUID getTransactionId() {
        return transactionId;
    }
}