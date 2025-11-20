package com.rewards_service.dto;

import com.rewards_service.domain.model.UserTier;

import java.time.Instant;
import java.util.UUID;

public class UserResponse {

    private UUID id;
    private String externalUserId;
    private String displayName;
    private String email;
    private String apiKey;
    private UserTier tier;
    private Instant createdAt;
    private Instant updatedAt;

    public UserResponse() {}

    public UserResponse(UUID id, String externalUserId, String displayName, String email,
                       String apiKey, UserTier tier, Instant createdAt, Instant updatedAt) {
        this.id = id;
        this.externalUserId = externalUserId;
        this.displayName = displayName;
        this.email = email;
        this.apiKey = apiKey;
        this.tier = tier;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Getters and setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getExternalUserId() { return externalUserId; }
    public void setExternalUserId(String externalUserId) { this.externalUserId = externalUserId; }

    public String getDisplayName() { return displayName; }
    public void setDisplayName(String displayName) { this.displayName = displayName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getApiKey() { return apiKey; }
    public void setApiKey(String apiKey) { this.apiKey = apiKey; }

    public UserTier getTier() { return tier; }
    public void setTier(UserTier tier) { this.tier = tier; }

    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }

    public Instant getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }
}