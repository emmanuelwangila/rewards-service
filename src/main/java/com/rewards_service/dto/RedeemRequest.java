package com.rewards_service.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public class RedeemRequest {

    @NotNull
    private UUID userId;

    @Min(1)
    private long points;

    public UUID getUserId() { return userId; }
    public void setUserId(UUID userId) { this.userId = userId; }
    public long getPoints() { return points; }
    public void setPoints(long points) { this.points = points; }
}
