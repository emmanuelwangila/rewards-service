package com.rewards_service.dto;

import java.util.UUID;

public class BalanceResponse {

    private UUID userId;
    private long points;
    private double balance;

    public BalanceResponse(UUID userId, long points, double balance) {
        this.userId = userId;
        this.points = points;
        this.balance = balance;
    }

    // Getters & Setters (or use Lombok)
    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public long getPoints() {
        return points;
    }

    public void setPoints(long points) {
        this.points = points;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
