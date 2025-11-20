package com.rewards_service.domain.model;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "wallet_entries")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WalletEntry {

    public enum WalletEntryType {
        CREDIT, DEBIT
    }

    @Id
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserAccount userAccount;

    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

    @Column(name = "currency", nullable = false, length = 3)
    private String currency;

    @Column(name = "description")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private WalletEntryType type;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    // Explicit private constructor for factory methods
    private WalletEntry(UUID id,
                        UserAccount userAccount,
                        BigDecimal amount,
                        String currency,
                        String description,
                        WalletEntryType type) {
        this.id = id;
        this.userAccount = userAccount;
        this.amount = amount;
        this.currency = currency;
        this.description = description;
        this.type = type;
    }

    @PrePersist
    void onCreate() {
        this.createdAt = Instant.now();
    }

    // Factory method for CREDIT entry
    public static WalletEntry credit(UserAccount user, BigDecimal amount, String currency, String description) {
        return new WalletEntry(
                UUID.randomUUID(),
                user,
                amount,
                currency,
                description,
                WalletEntryType.CREDIT
        );
    }

    // Optional: you can add DEBIT factory if needed
    public static WalletEntry debit(UserAccount user, BigDecimal amount, String currency, String description) {
        return new WalletEntry(
                UUID.randomUUID(),
                user,
                amount,
                currency,
                description,
                WalletEntryType.DEBIT
        );
    }
}
