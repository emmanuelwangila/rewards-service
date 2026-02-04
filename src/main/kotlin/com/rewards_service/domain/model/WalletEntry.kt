package com.rewards_service.domain.model

import jakarta.persistence.*
import java.math.BigDecimal
import java.time.Instant
import java.util.UUID

@Entity
@Table(name = "wallet_entries")
class WalletEntry protected constructor() {

    enum class WalletEntryType {
        CREDIT, DEBIT
    }

    @Id
    @Column(name = "id", nullable = false, updatable = false)
    var id: UUID? = null

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    var userAccount: UserAccount? = null

    @Column(name = "amount", nullable = false)
    var amount: BigDecimal? = null

    @Column(name = "currency", nullable = false, length = 3)
    var currency: String? = null

    @Column(name = "description")
    var description: String? = null

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    var type: WalletEntryType? = null

    @Column(name = "created_at", nullable = false)
    var createdAt: Instant? = null

    private constructor(
        id: UUID,
        userAccount: UserAccount,
        amount: BigDecimal,
        currency: String,
        description: String,
        type: WalletEntryType
    ) {
        this.id = id
        this.userAccount = userAccount
        this.amount = amount
        this.currency = currency
        this.description = description
        this.type = type
    }

    @PrePersist
    fun onCreate() {
        this.createdAt = Instant.now()
    }

    companion object {
        fun credit(user: UserAccount, amount: BigDecimal, currency: String, description: String): WalletEntry {
            return WalletEntry(
                UUID.randomUUID(),
                user,
                amount,
                currency,
                description,
                WalletEntryType.CREDIT
            )
        }

        fun debit(user: UserAccount, amount: BigDecimal, currency: String, description: String): WalletEntry {
            return WalletEntry(
                UUID.randomUUID(),
                user,
                amount,
                currency,
                description,
                WalletEntryType.DEBIT
            )
        }
    }
}
