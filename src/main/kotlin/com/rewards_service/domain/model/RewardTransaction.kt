package com.rewards_service.domain.model

import jakarta.persistence.*
import java.math.BigDecimal
import java.time.Instant
import java.util.UUID

@Entity
@Table(name = "reward_transactions")
class RewardTransaction protected constructor() {

    enum class TransactionType { EARN, REDEEM }

    @Id
    @Column(name = "id", nullable = false, updatable = false)
    var id: UUID? = null

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id")
    var userAccount: UserAccount? = null

    @Column(name = "points", nullable = false)
    var points: Long = 0

    @Column(name = "monetary_value")
    var monetaryValue: BigDecimal? = null

    @Column(name = "description")
    var description: String? = null

    @Column(name = "reference_id")
    var referenceId: String? = null

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    var type: TransactionType? = null

    @Column(name = "occurred_at", nullable = false)
    var occurredAt: Instant? = null

    private constructor(
        id: UUID,
        userAccount: UserAccount,
        points: Long,
        monetaryValue: BigDecimal?,
        description: String?,
        referenceId: String?,
        type: TransactionType
    ) : this() {
        this.id = id
        this.userAccount = userAccount
        this.points = points
        this.monetaryValue = monetaryValue
        this.description = description
        this.referenceId = referenceId
        this.type = type
    }

    @PrePersist
    fun onCreate() {
        this.occurredAt = Instant.now()
    }

    companion object {
        fun earn(user: UserAccount, points: Long, description: String, referenceId: String?): RewardTransaction {
            return RewardTransaction(UUID.randomUUID(), user, points, null, description, referenceId, TransactionType.EARN)
        }

        fun redeem(user: UserAccount, points: Long, monetaryValue: BigDecimal, description: String, referenceId: String?): RewardTransaction {
            return RewardTransaction(UUID.randomUUID(), user, points, monetaryValue, description, referenceId, TransactionType.REDEEM)
        }
    }
}