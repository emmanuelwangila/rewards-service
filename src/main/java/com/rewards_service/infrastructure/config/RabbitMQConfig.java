package com.rewards_service.infrastructure.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String REWARDS_EXCHANGE = "rewards.exchange";
    public static final String REWARD_EARNED_QUEUE = "reward.earned.queue";
    public static final String REWARD_REDEEMED_QUEUE = "reward.redeemed.queue";
    public static final String REWARD_EARNED_ROUTING_KEY = "reward.earned";
    public static final String REWARD_REDEEMED_ROUTING_KEY = "reward.redeemed";

    // Exchange
    @Bean
    public TopicExchange rewardsExchange() {
        return new TopicExchange(REWARDS_EXCHANGE);
    }

    // Queues
    @Bean
    public Queue rewardEarnedQueue() {
        return new Queue(REWARD_EARNED_QUEUE);
    }

    @Bean
    public Queue rewardRedeemedQueue() {
        return new Queue(REWARD_REDEEMED_QUEUE);
    }

    // Bindings
    @Bean
    public Binding rewardEarnedBinding(Queue rewardEarnedQueue, TopicExchange rewardsExchange) {
        return BindingBuilder.bind(rewardEarnedQueue).to(rewardsExchange).with(REWARD_EARNED_ROUTING_KEY);
    }

    @Bean
    public Binding rewardRedeemedBinding(Queue rewardRedeemedQueue, TopicExchange rewardsExchange) {
        return BindingBuilder.bind(rewardRedeemedQueue).to(rewardsExchange).with(REWARD_REDEEMED_ROUTING_KEY);
    }

    // JSON Message Converter
    @Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    // RabbitTemplate with JSON converter
    @Bean
    public RabbitTemplate rabbitTemplate(org.springframework.amqp.rabbit.connection.ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jackson2JsonMessageConverter());
        return rabbitTemplate;
    }
}
