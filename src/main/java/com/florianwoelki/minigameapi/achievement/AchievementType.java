package com.florianwoelki.minigameapi.achievement;

public enum AchievementType {

	;

	private String name;
	private String text;

	AchievementType(String name, String text) {
		this.name = name;
		this.text = text;
	}

	public String getName() {
		return name;
	}

	public String getText() {
		return text;
	}

}
