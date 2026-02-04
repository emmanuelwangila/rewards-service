package com.rewards_service.application.service

import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class RewardEventPublisher(private val rabbitTemplate: RabbitTemplate) {

    fun publishRewardEarned(userId: UUID, points: Long, description: String) {
        val event = RewardEvent(userId, "EARNED", points, description)
        rabbitTemplate.convertAndSend("rewards.exchange", "reward.earned", event)
        println("Published reward earned event for user $userId: $points points")
    }

    fun publishRewardRedeemed(userId: UUID, points: Long, description: String) {
        val event = RewardEvent(userId, "REDEEMED", points, description)
        rabbitTemplate.convertAndSend("rewards.exchange", "reward.redeemed", event)
        println("Published reward redeemed event for user $userId: $points points")
    }

    class RewardEvent {
        var userId: UUID? = null
        var type: String? = null
        var points: Long = 0L
        var description: String? = null

        constructor()

        constructor(userId: UUID?, type: String?, points: Long, description: String?) {
            this.userId = userId
            this.type = type
            this.points = points
            this.description = description
        }
    }
}
