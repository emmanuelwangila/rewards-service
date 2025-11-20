package com.rewards_service.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class AwardRequest {

    @NotNull
    private Long userId;

    @Min(1)
    private long amountCents; // e.g., amount in cents to derive points

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public long getAmountCents() { return amountCents; }
    public void setAmountCents(long amountCents) { this.amountCents = amountCents; }
}
