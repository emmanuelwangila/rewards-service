package com.rewards_service.infrastructure.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String REWARDS_EXCHANGE = "rewards.exchange";
    public static final String REWARD_EARNED_QUEUE = "reward.earned.queue";
    public static final String REWARD_REDEEMED_QUEUE = "reward.redeemed.queue";
    public static final String REWARD_EARNED_ROUTING_KEY = "reward.earned";
    public static final String REWARD_REDEEMED_ROUTING_KEY = "reward.redeemed";

    @Bean
    public TopicExchange rewardsExchange() {
        return new TopicExchange(REWARDS_EXCHANGE);
    }

    @Bean
    public Queue rewardEarnedQueue() {
        return new Queue(REWARD_EARNED_QUEUE);
    }

    @Bean
    public Queue rewardRedeemedQueue() {
        return new Queue(REWARD_REDEEMED_QUEUE);
    }

    @Bean
    public Binding rewardEarnedBinding(Queue rewardEarnedQueue, TopicExchange rewardsExchange) {
        return BindingBuilder.bind(rewardEarnedQueue).to(rewardsExchange).with(REWARD_EARNED_ROUTING_KEY);
    }

    @Bean
    public Binding rewardRedeemedBinding(Queue rewardRedeemedQueue, TopicExchange rewardsExchange) {
        return BindingBuilder.bind(rewardRedeemedQueue).to(rewardsExchange).with(REWARD_REDEEMED_ROUTING_KEY);
    }
}