package com.rewards_service.dto

import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotNull
import java.util.UUID

class AwardRequest {

    @NotNull
    var userId: UUID? = null

    @Min(1)
    var amountCents: Long = 0L
}
