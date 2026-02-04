package com.rewards_service.dto

import java.util.UUID

class BalanceResponse {

    var userId: UUID? = null
    var points: Long = 0L
    var balance: Double = 0.0

    constructor(userId: UUID?, points: Long, balance: Double) {
        this.userId = userId
        this.points = points
        this.balance = balance
    }
}
