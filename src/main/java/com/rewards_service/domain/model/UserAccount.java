package com.rewards_service.domain.model;

import java.time.Instant;
import java.util.UUID;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_accounts")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserAccount {

    @Id
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @Column(name = "external_user_id", nullable = false, unique = true)
    private String externalUserId;

    @Column(name = "display_name", nullable = false)
    private String displayName;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "api_key", nullable = false, unique = true, length = 64)
    private String apiKey;

    @Enumerated(EnumType.STRING)
    @Column(name = "tier", nullable = false)
    private UserTier tier;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    // Explicit constructor to match your original design — keeps fields immutable after creation
    public UserAccount(UUID id,
                       String externalUserId,
                       String displayName,
                       String email,
                       String apiKey,
                       UserTier tier) {
        this.id = id;
        this.externalUserId = externalUserId;
        this.displayName = displayName;
        this.email = email;
        this.apiKey = apiKey;
        this.tier = tier;
    }

    @PrePersist
    void onCreate() {
        this.createdAt = Instant.now();
        this.updatedAt = this.createdAt;
    }

    @PreUpdate
    void onUpdate() {
        this.updatedAt = Instant.now();
    }

    // Business method — intentionally not removed
    public void rotateApiKey() {
        this.apiKey = UUID.randomUUID().toString().replace("-", "");
    }
}
