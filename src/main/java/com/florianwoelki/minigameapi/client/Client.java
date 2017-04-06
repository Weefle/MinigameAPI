package com.florianwoelki.minigameapi.client;

import java.util.UUID;

import com.florianwoelki.minigameapi.achievement.Achievement;
import com.florianwoelki.minigameapi.uuid.UUIDFetcher;

public class Client {

	private String name;
	private UUID uuid;

	private char[] achievements;

	public Client(UUID uuid) {
		this.uuid = uuid;
		this.name = UUIDFetcher.getName(uuid);
	}

	public Client(UUID uuid, char[] achievements) {
		this.uuid = uuid;
		this.achievements = achievements;
		this.name = UUIDFetcher.getName(uuid);
	}

	public boolean hasAchievement(Achievement achievement) {
		return achievements[achievement.getId()] == 'a';
	}

	public void awardAchievement(Achievement achievement) {
		achievements[achievement.getId()] = 'a';
	}

	public String getName() {
		return name;
	}

	public UUID getUuid() {
		return uuid;
	}

}
