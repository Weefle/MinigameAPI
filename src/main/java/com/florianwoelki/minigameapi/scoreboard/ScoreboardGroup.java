package com.florianwoelki.minigameapi.scoreboard;

/**
 * The Class ScoreboardGroup.
 */
public class ScoreboardGroup {

	/** The id. */
	private String id;
	
	/** The display name. */
	private String displayName;
	
	/** The prefix. */
	private String prefix;
	
	/** The permission. */
	private String permission;

	/**
	 * Instantiates a new scoreboard group.
	 *
	 * @param id the id
	 * @param displayName the display name
	 * @param prefix the prefix
	 */
	public ScoreboardGroup(String id, String displayName, String prefix) {
		this.id = id;
		this.displayName = displayName;
		this.prefix = prefix;
	}

	/**
	 * Instantiates a new scoreboard group.
	 *
	 * @param id the id
	 * @param displayName the display name
	 * @param prefix the prefix
	 * @param permission the permission
	 */
	public ScoreboardGroup(String id, String displayName, String prefix, String permission) {
		this.id = id;
		this.displayName = displayName;
		this.prefix = prefix;
		this.permission = permission;
	}

	/**
	 * Gets the permission.
	 *
	 * @return the permission
	 */
	public String getPermission() {
		return permission;
	}

	/**
	 * Gets the display name.
	 *
	 * @return the display name
	 */
	public String getDisplayName() {
		return displayName;
	}

	/**
	 * Gets the prefix.
	 *
	 * @return the prefix
	 */
	public String getPrefix() {
		return prefix;
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public String getId() {
		return id;
	}

}
