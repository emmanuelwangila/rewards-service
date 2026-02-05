package com.rewards_service.infrastructure.repository

import com.rewards_service.domain.model.UserAccount
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.Optional
import java.util.UUID

@Repository
interface UserAccountRepository : JpaRepository<UserAccount, UUID> {
    fun findByApiKey(apiKey: String): Optional<UserAccount>
    fun findByExternalUserId(externalUserId: String): Optional<UserAccount>
    fun findByEmail(email: String): Optional<UserAccount>
}
