package com.rewards_service.domain.model

import jakarta.persistence.*
import java.time.Instant
import java.util.UUID

@Entity
@Table(name = "user_accounts")
class UserAccount protected constructor() {

    @Id
    @Column(name = "id", nullable = false, updatable = false)
    var id: UUID? = null

    @Column(name = "external_user_id", nullable = false, unique = true)
    var externalUserId: String? = null

    @Column(name = "display_name", nullable = false)
    var displayName: String? = null

    @Column(name = "email", nullable = false, unique = true)
    var email: String? = null

    @Column(name = "api_key", nullable = false, unique = true, length = 64)
    var apiKey: String? = null

    @Enumerated(EnumType.STRING)
    @Column(name = "tier", nullable = false)
    var tier: UserTier? = null

    @Column(name = "created_at", nullable = false)
    var createdAt: Instant? = null

    @Column(name = "updated_at", nullable = false)
    var updatedAt: Instant? = null

    constructor(
        id: UUID,
        externalUserId: String,
        displayName: String,
        email: String,
        apiKey: String,
        tier: UserTier
    ) : this() {
        this.id = id
        this.externalUserId = externalUserId
        this.displayName = displayName
        this.email = email
        this.apiKey = apiKey
        this.tier = tier
    }

    @PrePersist
    fun onCreate() {
        this.createdAt = Instant.now()
        this.updatedAt = this.createdAt
    }

    @PreUpdate
    fun onUpdate() {
        this.updatedAt = Instant.now()
    }

    fun rotateApiKey() {
        this.apiKey = UUID.randomUUID().toString().replace("-", "")
    }
}
