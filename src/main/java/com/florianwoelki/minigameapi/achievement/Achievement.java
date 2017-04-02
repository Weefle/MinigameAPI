package com.florianwoelki.minigameapi.achievement;

import org.bukkit.entity.Player;

public class Achievement {

	private AchievementHandler achievementHandler;

	private AchievementType achievementType;

	public Achievement(AchievementHandler achievementHandler, AchievementType achievementType) {
		this.achievementHandler = achievementHandler;
		this.achievementType = achievementType;
	}

	public boolean hasAchievement(Player player) {
		return false;
	}

	public void giveAchievement(Player player) {

	}

	public AchievementType getAchievementType() {
		return achievementType;
	}

}
