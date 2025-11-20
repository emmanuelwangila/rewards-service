package com.rewards_service.infrastructure.web;

import com.rewards_service.application.service.RewardService;
import com.rewards_service.dto.AwardRequest;
import com.rewards_service.dto.BalanceResponse;
import com.rewards_service.dto.RedeemRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/rewards")
@RequiredArgsConstructor
@Tag(name = "Rewards", description = "Rewards management API")
public class RewardController {

    private final RewardService rewardService;

    @PostMapping("/award")
    @Operation(summary = "Award points to user")
    public ResponseEntity<Void> awardPoints(@Valid @RequestBody AwardRequest request) {
        // In real app, get user from auth, but for now use request.userId
        rewardService.awardPoints(request.getUserId(), request.getAmountCents(), "api-award");
        return ResponseEntity.ok().build();
    }

    @PostMapping("/redeem")
    @Operation(summary = "Redeem points for money")
    public ResponseEntity<Void> redeemPoints(@Valid @RequestBody RedeemRequest request) {
        rewardService.redeemPoints(request.getUserId(), request.getPoints());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/balance/{userId}")
    @Operation(summary = "Get user balance")
    public ResponseEntity<BalanceResponse> getBalance(@PathVariable UUID userId) {
        RewardService.BalanceResponse balance = rewardService.getBalance(userId);
        BalanceResponse response = new BalanceResponse(balance.getUserId(), balance.getPoints(), balance.getBalance());
        return ResponseEntity.ok(response);
    }
}