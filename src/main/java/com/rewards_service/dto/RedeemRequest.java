package com.rewards_service.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class RedeemRequest {

    @NotNull
    private Long userId;

    @Min(1)
    private long points;

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public long getPoints() { return points; }
    public void setPoints(long points) { this.points = points; }
}
