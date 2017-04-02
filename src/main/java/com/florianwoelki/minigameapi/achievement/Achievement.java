package com.florianwoelki.minigameapi.achievement;

public class Achievement {

	private AchievementHandler achievementHandler;

	public void register(AchievementHandler achievementHandler) {
		this.achievementHandler = achievementHandler;
		achievementHandler.getAchievementList().add(this);
	}

}
