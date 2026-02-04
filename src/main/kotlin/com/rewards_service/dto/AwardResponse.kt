package com.rewards_service.dto

import java.util.UUID

class AwardResponse {

    var userId: UUID? = null
    var pointsAwarded: Long = 0L
    var transactionId: UUID? = null

    constructor(userId: UUID?, pointsAwarded: Long, transactionId: UUID?) {
        this.userId = userId
        this.pointsAwarded = pointsAwarded
        this.transactionId = transactionId
    }
}
