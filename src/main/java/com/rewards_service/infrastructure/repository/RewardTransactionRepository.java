package com.rewards_service.infrastructure.repository;

import com.rewards_service.domain.model.RewardTransaction;
import com.rewards_service.domain.model.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RewardTransactionRepository extends JpaRepository<RewardTransaction, UUID> {
    List<RewardTransaction> findByUserAccountOrderByOccurredAtDesc(UserAccount userAccount);
}