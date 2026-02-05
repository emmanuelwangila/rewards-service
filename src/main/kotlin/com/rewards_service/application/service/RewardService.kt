package com.rewards_service.application.service

import com.rewards_service.domain.model.*
import com.rewards_service.dto.AwardResponse
import com.rewards_service.dto.RedeemResponse
import com.rewards_service.infrastructure.repository.RewardTransactionRepository
import com.rewards_service.infrastructure.repository.UserAccountRepository
import com.rewards_service.infrastructure.repository.WalletEntryRepository
import com.rewards_service.infrastructure.repository.WalletRepository
import org.slf4j.LoggerFactory
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal
import java.util.UUID

@Service
class RewardService(
    private val userAccountRepository: UserAccountRepository,
    private val walletRepository: WalletRepository,
    private val rewardTransactionRepository: RewardTransactionRepository,
    private val walletEntryRepository: WalletEntryRepository,
    private val eventPublisher: RewardEventPublisher
) {

    companion object {
        private val log = LoggerFactory.getLogger(RewardService::class.java)
        private const val POINTS_PER_DOLLAR = 1L
        private val DOLLARS_PER_POINT = BigDecimal.valueOf(0.01)
    }

    @Transactional
    @CacheEvict(value = ["walletBalance"], key = "#userId")
    fun awardPoints(userId: UUID, amountCents: Long, referenceId: String?): AwardResponse {
        val user = userAccountRepository.findById(userId)
            .orElseThrow { IllegalArgumentException("User not found") }

        val amountDollars = amountCents / 100.0
        val basePoints = (amountDollars * POINTS_PER_DOLLAR).toLong()
        val points = (basePoints * user.tier!!.multiplier).toLong()

        val transaction = RewardTransaction.earn(user, points, "Awarded for purchase", referenceId)
        rewardTransactionRepository.save(transaction)

        val wallet = getOrCreateWallet(user)
        wallet.points = wallet.points + points
        walletRepository.save(wallet)

        // eventPublisher.publishRewardEarned(userId, points, "Awarded for purchase")

        log.info("Awarded {} points to user {}", points, userId)

        return AwardResponse(user.id, points, transaction.id)
    }

    @Transactional
    @CacheEvict(value = ["walletBalance"], key = "#userId")
    fun redeemPoints(userId: UUID, points: Long): RedeemResponse {
        val user = userAccountRepository.findById(userId)
            .orElseThrow { IllegalArgumentException("User not found") }

        val wallet = walletRepository.findByUserAccount(user)
            .orElseThrow { IllegalArgumentException("Wallet not found") }

        if (wallet.points < points) {
            throw IllegalArgumentException("Insufficient points")
        }

        val monetaryValue = BigDecimal.valueOf(points).multiply(DOLLARS_PER_POINT)

        val transaction = RewardTransaction.redeem(user, points, monetaryValue, "Redeemed to wallet", null)
        rewardTransactionRepository.save(transaction)

        wallet.points = wallet.points - points
        wallet.balance = wallet.balance + monetaryValue.toDouble()
        walletRepository.save(wallet)

        val entry = WalletEntry.credit(user, BigDecimal.valueOf(monetaryValue.toDouble()), "USD", "Points redemption")
        walletEntryRepository.save(entry)

        // eventPublisher.publishRewardRedeemed(userId, points, "Redeemed to wallet")

        log.info("Redeemed {} points for {} to user {}", points, monetaryValue, userId)

        return RedeemResponse(user.id, points, monetaryValue, transaction.id)
    }

    @Cacheable(value = ["walletBalance"], key = "#userId")
    fun getBalance(userId: UUID): com.rewards_service.dto.BalanceResponse {
        val user = userAccountRepository.findById(userId)
            .orElseThrow { IllegalArgumentException("User not found") }

        val wallet = walletRepository.findByUserAccount(user)
            .orElseGet { createWallet(user) }

        return com.rewards_service.dto.BalanceResponse(user.id, wallet.points, wallet.balance)
    }

    private fun getOrCreateWallet(user: UserAccount): Wallet {
        return walletRepository.findByUserAccount(user)
            .orElseGet { createWallet(user) }
    }

    private fun createWallet(user: UserAccount): Wallet {
        val wallet = Wallet(user)
        return walletRepository.save(wallet)
    }
}