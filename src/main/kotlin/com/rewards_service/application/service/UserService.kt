package com.rewards_service.application.service

import com.rewards_service.domain.model.UserAccount
import com.rewards_service.domain.model.UserTier
import com.rewards_service.infrastructure.repository.UserAccountRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.Optional
import java.util.UUID

@Service
class UserService(private val userAccountRepository: UserAccountRepository) {

    @Transactional
    fun createUser(externalUserId: String, displayName: String, email: String, tier: UserTier): UserAccount {
        if (userAccountRepository.findByExternalUserId(externalUserId).isPresent) {
            throw IllegalArgumentException("User already exists")
        }
        if (userAccountRepository.findByEmail(email).isPresent) {
            throw IllegalArgumentException("Email already in use")
        }

        val apiKey = generateApiKey()
        val user = UserAccount(UUID.randomUUID(), externalUserId, displayName, email, apiKey, tier)
        return userAccountRepository.save(user)
    }

    fun findByApiKey(apiKey: String): Optional<UserAccount> {
        return userAccountRepository.findByApiKey(apiKey)
    }

    fun findById(id: UUID): Optional<UserAccount> {
        return userAccountRepository.findById(id)
    }

    @Transactional
    fun rotateApiKey(userId: UUID): UserAccount {
        val user = userAccountRepository.findById(userId)
            .orElseThrow { IllegalArgumentException("User not found") }

        user.rotateApiKey()
        return userAccountRepository.save(user)
    }

    private fun generateApiKey(): String {
        return UUID.randomUUID().toString().replace("-", "")
    }
}
