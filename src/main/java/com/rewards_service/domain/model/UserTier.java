package com.rewards_service.domain.model;

public enum UserTier {
	STANDARD(1.0),
	SILVER(1.10),
	GOLD(1.25),
	PLATINUM(1.50);

	private final double multiplier;

	UserTier(double multiplier) {
		this.multiplier = multiplier;
	}

	public double multiplier() {
		return multiplier;
	}
}

