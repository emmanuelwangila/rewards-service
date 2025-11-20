package com.rewards_service.dto;

import com.rewards_service.domain.model.UserTier;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class UserCreateRequest {

    @NotBlank
    private String externalUserId;

    @NotBlank
    private String displayName;

    @Email
    @NotBlank
    private String email;

    @NotNull
    private UserTier tier;

    public UserCreateRequest() {}

    public UserCreateRequest(String externalUserId, String displayName, String email, UserTier tier) {
        this.externalUserId = externalUserId;
        this.displayName = displayName;
        this.email = email;
        this.tier = tier;
    }

    // Getters and setters
    public String getExternalUserId() { return externalUserId; }
    public void setExternalUserId(String externalUserId) { this.externalUserId = externalUserId; }

    public String getDisplayName() { return displayName; }
    public void setDisplayName(String displayName) { this.displayName = displayName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public UserTier getTier() { return tier; }
    public void setTier(UserTier tier) { this.tier = tier; }
}