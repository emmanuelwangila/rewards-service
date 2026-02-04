package com.rewards_service.infrastructure.repository

import com.rewards_service.domain.model.UserAccount
import com.rewards_service.domain.model.WalletEntry
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface WalletEntryRepository : JpaRepository<WalletEntry, UUID> {
    fun findByUserAccountOrderByCreatedAtDesc(userAccount: UserAccount): List<WalletEntry>
}
