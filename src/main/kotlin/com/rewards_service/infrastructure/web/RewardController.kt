package com.rewards_service.infrastructure.web

import com.rewards_service.application.service.RewardService
import com.rewards_service.dto.AwardRequest
import com.rewards_service.dto.AwardResponse
import com.rewards_service.dto.BalanceResponse
import com.rewards_service.dto.RedeemRequest
import com.rewards_service.dto.RedeemResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/rewards")
@Tag(name = "Rewards", description = "Rewards management API")
class RewardController(private val rewardService: RewardService) {

    @PostMapping("/award")
    @Operation(summary = "Award points to user")
    fun awardPoints(@Valid @RequestBody request: AwardRequest): ResponseEntity<AwardResponse> {
        val response = rewardService.awardPoints(request.userId!!, request.amountCents, "api-award")
        return ResponseEntity.ok(response)
    }

    @PostMapping("/redeem")
    @Operation(summary = "Redeem points for money")
    fun redeemPoints(@Valid @RequestBody request: RedeemRequest): ResponseEntity<RedeemResponse> {
        val response = rewardService.redeemPoints(request.userId!!, request.points)
        return ResponseEntity.ok(response)
    }

    @GetMapping("/balance/{userId}")
    @Operation(summary = "Get user balance")
    fun getBalance(@PathVariable userId: java.util.UUID): ResponseEntity<BalanceResponse> {
        val response = rewardService.getBalance(userId)
        return ResponseEntity.ok(response)
    }
}
