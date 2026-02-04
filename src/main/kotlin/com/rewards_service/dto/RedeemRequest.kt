package com.rewards_service.dto

import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotNull
import java.util.UUID

class RedeemRequest {

    @NotNull
    var userId: UUID? = null

    @Min(1)
    var points: Long = 0L
}
