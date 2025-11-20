package com.rewards_service.application.service;

import com.rewards_service.domain.model.UserAccount;
import com.rewards_service.domain.model.UserTier;
import com.rewards_service.infrastructure.repository.UserAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserAccountRepository userAccountRepository;

    @Transactional
    public UserAccount createUser(String externalUserId, String displayName, String email, UserTier tier) {
        if (userAccountRepository.findByExternalUserId(externalUserId).isPresent()) {
            throw new IllegalArgumentException("User already exists");
        }
        if (userAccountRepository.findByEmail(email).isPresent()) {
            throw new IllegalArgumentException("Email already in use");
        }

        String apiKey = generateApiKey();
        UserAccount user = new UserAccount(UUID.randomUUID(), externalUserId, displayName, email, apiKey, tier);
        return userAccountRepository.save(user);
    }

    public Optional<UserAccount> findByApiKey(String apiKey) {
        return userAccountRepository.findByApiKey(apiKey);
    }

    public Optional<UserAccount> findById(UUID id) {
        return userAccountRepository.findById(id);
    }

    @Transactional
    public UserAccount rotateApiKey(UUID userId) {
        UserAccount user = userAccountRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        user.rotateApiKey();
        return userAccountRepository.save(user);
    }

    private String generateApiKey() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}