package com.rewards_service.infrastructure.repository

import com.rewards_service.domain.model.UserAccount
import com.rewards_service.domain.model.Wallet
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.Optional
import java.util.UUID

@Repository
interface WalletRepository : JpaRepository<Wallet, Long> {
    fun findByUserAccount(userAccount: UserAccount): Optional<Wallet>
    fun findByUserAccountId(userAccountId: UUID): Optional<Wallet>
}