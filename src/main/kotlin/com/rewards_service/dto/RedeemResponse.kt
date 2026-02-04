package com.rewards_service.dto

import java.math.BigDecimal
import java.util.UUID

class RedeemResponse {

    var userId: UUID? = null
    var pointsRedeemed: Long = 0L
    var monetaryValue: BigDecimal? = null
    var transactionId: UUID? = null

    constructor(userId: UUID?, pointsRedeemed: Long, monetaryValue: BigDecimal?, transactionId: UUID?) {
        this.userId = userId
        this.pointsRedeemed = pointsRedeemed
        this.monetaryValue = monetaryValue
        this.transactionId = transactionId
    }
}
