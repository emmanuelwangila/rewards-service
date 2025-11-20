package com.rewards_service.infrastructure.repository;

import com.rewards_service.domain.model.UserAccount;
import com.rewards_service.domain.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, Long> {
    Optional<Wallet> findByUserAccount(UserAccount userAccount);
    Optional<Wallet> findByUserAccountId(UUID userAccountId);
}