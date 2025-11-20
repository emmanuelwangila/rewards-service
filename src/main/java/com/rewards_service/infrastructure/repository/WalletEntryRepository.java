package com.rewards_service.infrastructure.repository;

import com.rewards_service.domain.model.UserAccount;
import com.rewards_service.domain.model.WalletEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface WalletEntryRepository extends JpaRepository<WalletEntry, UUID> {
    List<WalletEntry> findByUserAccountOrderByCreatedAtDesc(UserAccount userAccount);
}