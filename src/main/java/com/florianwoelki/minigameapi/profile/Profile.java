package com.florianwoelki.minigameapi.profile;

import java.util.UUID;

import org.bukkit.entity.Player;

import com.florianwoelki.minigameapi.achievement.Achievement;

public class Profile {

	private UUID uuid;
	private String name;

	private char[] achievements;

	private boolean isLoaded;

	public Profile(Player player) {
		this.uuid = player.getUniqueId();
		this.name = player.getName();
	}

	public boolean hasAchievement(Achievement achievement) {
		return achievements[achievement.getId()] == 'd';
	}

	public void awardAchievement(Achievement achievement) {
		achievements[achievement.getId()] = 'd';
	}

	public UUID getUuid() {
		return uuid;
	}

	public String getName() {
		return name;
	}

	public void setLoaded(boolean isLoaded) {
		this.isLoaded = isLoaded;
	}

	public boolean isLoaded() {
		return isLoaded;
	}

	public void setAchievements(char[] achievements) {
		this.achievements = achievements;
	}

	public char[] getAchievements() {
		return achievements;
	}

}
