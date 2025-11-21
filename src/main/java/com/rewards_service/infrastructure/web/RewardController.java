package com.rewards_service.infrastructure.web;

import com.rewards_service.application.service.RewardService;
import com.rewards_service.dto.AwardRequest;
import com.rewards_service.dto.AwardResponse;
import com.rewards_service.dto.BalanceResponse;
import com.rewards_service.dto.RedeemRequest;
import com.rewards_service.dto.RedeemResponse;
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
    public ResponseEntity<AwardResponse> awardPoints(@Valid @RequestBody AwardRequest request) {
        // In real app, get user from auth, but for now use request.userId
        AwardResponse response = rewardService.awardPoints(request.getUserId(), request.getAmountCents(), "api-award");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/redeem")
    @Operation(summary = "Redeem points for money")
    public ResponseEntity<RedeemResponse> redeemPoints(@Valid @RequestBody RedeemRequest request) {
        RedeemResponse response = rewardService.redeemPoints(request.getUserId(), request.getPoints());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/balance/{userId}")
    @Operation(summary = "Get user balance")
    public ResponseEntity<BalanceResponse> getBalance(@PathVariable UUID userId) {
        BalanceResponse response = rewardService.getBalance(userId);
        return ResponseEntity.ok(response);
    }
}