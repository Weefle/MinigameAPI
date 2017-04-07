package com.florianwoelki.minigameapi.profile;

import java.util.UUID;

import org.bukkit.entity.Player;

import com.florianwoelki.minigameapi.achievement.Achievement;

/**
 * The Class Profile.
 */
public class Profile {

	/** The uuid. */
	private UUID uuid;

	/** The name. */
	private String name;

	/** The achievements. */
	private char[] achievements;

	/** The is loaded. */
	private boolean isLoaded;

	/**
	 * Instantiates a new profile.
	 *
	 * @param player
	 *            the player
	 */
	public Profile(Player player) {
		this.uuid = player.getUniqueId();
		this.name = player.getName();
	}

	/**
	 * Checks for achievement.
	 *
	 * @param achievement
	 *            the achievement
	 * @return true, if successful
	 */
	public boolean hasAchievement(Achievement achievement) {
		return achievements[achievement.getId()] == 'd';
	}

	/**
	 * Award achievement.
	 *
	 * @param achievement
	 *            the achievement
	 */
	public void awardAchievement(Achievement achievement) {
		achievements[achievement.getId()] = 'd';
	}

	/**
	 * Gets the uuid.
	 *
	 * @return the uuid
	 */
	public UUID getUuid() {
		return uuid;
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the loaded.
	 *
	 * @param isLoaded
	 *            the new loaded
	 */
	public void setLoaded(boolean isLoaded) {
		this.isLoaded = isLoaded;
	}

	/**
	 * Checks if is loaded.
	 *
	 * @return true, if is loaded
	 */
	public boolean isLoaded() {
		return isLoaded;
	}

	/**
	 * Sets the achievements.
	 *
	 * @param achievements
	 *            the new achievements
	 */
	public void setAchievements(char[] achievements) {
		this.achievements = achievements;
	}

	/**
	 * Gets the achievements.
	 *
	 * @return the achievements
	 */
	public char[] getAchievements() {
		return achievements;
	}

}
