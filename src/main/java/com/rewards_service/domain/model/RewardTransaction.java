package com.rewards_service.domain.model;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "reward_transactions")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RewardTransaction {

    public enum TransactionType {
        EARN, REDEEM
    }

    @Id
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id")
    private UserAccount userAccount;

    @Column(name = "points", nullable = false)
    private long points;

    @Column(name = "monetary_value")
    private BigDecimal monetaryValue;

    @Column(name = "description")
    private String description;

    @Column(name = "reference_id")
    private String referenceId;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private TransactionType type;

    @Column(name = "occurred_at", nullable = false)
    private Instant occurredAt;

    // Explicit private all-args constructor — ensures compiler sees it (no Lombok dependency here)
    private RewardTransaction(UUID id,
                              UserAccount userAccount,
                              long points,
                              BigDecimal monetaryValue,
                              String description,
                              String referenceId,
                              TransactionType type) {
        this.id = id;
        this.userAccount = userAccount;
        this.points = points;
        this.monetaryValue = monetaryValue;
        this.description = description;
        this.referenceId = referenceId;
        this.type = type;
    }

    @PrePersist
    void onCreate() {
        this.occurredAt = Instant.now();
    }

    public static RewardTransaction earn(UserAccount user, long points, String description, String referenceId) {
        // pass explicit null cast to be extra-safe, or keep null since constructor exists
        return new RewardTransaction(
                UUID.randomUUID(),
                user,
                points,
                /* monetaryValue = */ (BigDecimal) null,
                description,
                referenceId,
                TransactionType.EARN
        );
    }

    public static RewardTransaction redeem(
            UserAccount user,
            long points,
            BigDecimal monetaryValue,
            String description,
            String referenceId
    ) {
        return new RewardTransaction(
                UUID.randomUUID(),
                user,
                points,
                monetaryValue,
                description,
                referenceId,
                TransactionType.REDEEM
        );
    }
}
