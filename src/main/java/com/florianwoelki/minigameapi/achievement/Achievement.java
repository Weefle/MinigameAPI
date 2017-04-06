package com.florianwoelki.minigameapi.achievement;

public class Achievement {

	private AchievementHandler achievementHandler;

	private int id;
	private String name;
	private String description;

	public Achievement(AchievementHandler achievementHandler, int id, String name, String description) {
		this.achievementHandler = achievementHandler;
		this.id = id;
		this.name = name;
		this.description = description;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

}
