package com.rewards_service.infrastructure.repository;

import com.rewards_service.domain.model.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserAccountRepository extends JpaRepository<UserAccount, UUID> {
    Optional<UserAccount> findByApiKey(String apiKey);
    Optional<UserAccount> findByExternalUserId(String externalUserId);
    Optional<UserAccount> findByEmail(String email);
}