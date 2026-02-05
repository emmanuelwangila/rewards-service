package com.rewards_service.infrastructure.repository

import com.rewards_service.domain.model.RewardTransaction
import com.rewards_service.domain.model.UserAccount
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface RewardTransactionRepository : JpaRepository<RewardTransaction, UUID> {
    fun findByUserAccountOrderByOccurredAtDesc(userAccount: UserAccount): List<RewardTransaction>
}
