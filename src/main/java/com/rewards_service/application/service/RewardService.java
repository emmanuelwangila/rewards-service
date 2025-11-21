package com.rewards_service.application.service;

import com.rewards_service.domain.model.*;
import com.rewards_service.dto.AwardResponse;
import com.rewards_service.dto.BalanceResponse;
import com.rewards_service.dto.RedeemResponse;
import com.rewards_service.infrastructure.repository.RewardTransactionRepository;
import com.rewards_service.infrastructure.repository.UserAccountRepository;
import com.rewards_service.infrastructure.repository.WalletEntryRepository;
import com.rewards_service.infrastructure.repository.WalletRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class RewardService {

    private static final long POINTS_PER_DOLLAR = 100; // 1 point per $1
    private static final BigDecimal DOLLARS_PER_POINT = BigDecimal.valueOf(0.01); // $0.01 per point

    private final UserAccountRepository userAccountRepository;
    private final WalletRepository walletRepository;
    private final RewardTransactionRepository rewardTransactionRepository;
    private final WalletEntryRepository walletEntryRepository;
    private final RewardEventPublisher eventPublisher;

    @Transactional
    @CacheEvict(value = "walletBalance", key = "#userId")
    public AwardResponse awardPoints(UUID userId, long amountCents, String referenceId) {
        UserAccount user = userAccountRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        // Calculate points: amount in dollars * tier multiplier
        double amountDollars = amountCents / 100.0;
        long basePoints = (long) (amountDollars * POINTS_PER_DOLLAR);
        long points = (long) (basePoints * user.getTier().multiplier());

        // Create transaction
        RewardTransaction transaction = RewardTransaction.earn(user, points, "Awarded for purchase", referenceId);
        rewardTransactionRepository.save(transaction);

        // Update wallet
        Wallet wallet = getOrCreateWallet(user);
        wallet.setPoints(wallet.getPoints() + points);
        walletRepository.save(wallet);

        // eventPublisher.publishRewardEarned(userId, points, "Awarded for purchase");

        log.info("Awarded {} points to user {}", points, userId);

        return new AwardResponse(user.getId(), points, transaction.getId());
    }

    @Transactional
    @CacheEvict(value = "walletBalance", key = "#userId")
    public RedeemResponse redeemPoints(UUID userId, long points) {
        UserAccount user = userAccountRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Wallet wallet = walletRepository.findByUserAccount(user)
                .orElseThrow(() -> new IllegalArgumentException("Wallet not found"));

        if (wallet.getPoints() < points) {
            throw new IllegalArgumentException("Insufficient points");
        }

        // Calculate monetary value
        BigDecimal monetaryValue = BigDecimal.valueOf(points).multiply(DOLLARS_PER_POINT);

        // Create transaction
        RewardTransaction transaction = RewardTransaction.redeem(user, points, monetaryValue, "Redeemed to wallet", null);
        rewardTransactionRepository.save(transaction);

        // Update wallet
        wallet.setPoints(wallet.getPoints() - points);
        wallet.setBalance(wallet.getBalance() + monetaryValue.doubleValue());
        walletRepository.save(wallet);

        // Create wallet entry
        WalletEntry entry = WalletEntry.credit(user, monetaryValue, "USD", "Points redemption");
        walletEntryRepository.save(entry);

        // eventPublisher.publishRewardRedeemed(userId, points, "Redeemed to wallet");

        log.info("Redeemed {} points for ${} to user {}", points, monetaryValue, userId);

        return new RedeemResponse(user.getId(), points, monetaryValue, transaction.getId());
    }

    @Cacheable(value = "walletBalance", key = "#userId")
    public com.rewards_service.dto.BalanceResponse getBalance(UUID userId) {
        UserAccount user = userAccountRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Wallet wallet = walletRepository.findByUserAccount(user)
                .orElseGet(() -> createWallet(user));

        return new com.rewards_service.dto.BalanceResponse(user.getId(), wallet.getPoints(), wallet.getBalance());
    }

    private Wallet getOrCreateWallet(UserAccount user) {
        return walletRepository.findByUserAccount(user)
                .orElseGet(() -> createWallet(user));
    }

    private Wallet createWallet(UserAccount user) {
        Wallet wallet = new Wallet(user);
        return walletRepository.save(wallet);
    }

}