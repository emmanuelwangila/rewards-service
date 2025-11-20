package com.rewards_service;

import org.springframework.boot.SpringApplication;

public class TestRewardsServiceApplication {

	public static void main(String[] args) {
		SpringApplication.from(RewardsServiceApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
