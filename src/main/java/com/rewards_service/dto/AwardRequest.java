package com.rewards_service.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public class AwardRequest {

    @NotNull
    private UUID userId;

    @Min(1)
    private long amountCents; // e.g., amount in cents to derive points

    public UUID getUserId() { return userId; }
    public void setUserId(UUID userId) { this.userId = userId; }
    public long getAmountCents() { return amountCents; }
    public void setAmountCents(long amountCents) { this.amountCents = amountCents; }
}
