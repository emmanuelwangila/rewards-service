package com.rewards_service.domain.model

enum class UserTier(val multiplier: Double) {
    STANDARD(1.0),
    SILVER(1.10),
    GOLD(1.25),
    PLATINUM(1.50)
}
