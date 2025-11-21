package com.rewards_service.dto;

import java.math.BigDecimal;
import java.util.UUID;

public class RedeemResponse {

    private UUID userId;
    private long pointsRedeemed;
    private BigDecimal monetaryValue;
    private UUID transactionId;

    public RedeemResponse(UUID userId, long pointsRedeemed, BigDecimal monetaryValue, UUID transactionId) {
        this.userId = userId;
        this.pointsRedeemed = pointsRedeemed;
        this.monetaryValue = monetaryValue;
        this.transactionId = transactionId;
    }

    // Getters
    public UUID getUserId() {
        return userId;
    }

    public long getPointsRedeemed() {
        return pointsRedeemed;
    }

    public BigDecimal getMonetaryValue() {
        return monetaryValue;
    }

    public UUID getTransactionId() {
        return transactionId;
    }
}