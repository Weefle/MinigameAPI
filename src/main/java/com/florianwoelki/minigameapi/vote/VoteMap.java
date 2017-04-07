package com.florianwoelki.minigameapi.vote;

import org.bukkit.World;

/**
 * This class represents a voted map
 * 
 * With this class you can add VoteMaps for the VoteManager
 */
public class VoteMap {

	private final String DISPLAY_NAME;
	private final String WORLD_NAME;

	private World world;

	/**
	 * Constructor with specific displayName and worldName
	 * 
	 * @param displayName
	 *            String for the display name
	 * @param worldName
	 *            String for the world name
	 */
	public VoteMap(String displayName, String worldName) {
		this.DISPLAY_NAME = displayName;
		this.WORLD_NAME = worldName;
	}

	/**
	 * Get the display name
	 * 
	 * @return String with the display name
	 */
	public String getDisplayName() {
		return DISPLAY_NAME;
	}

	/**
	 * Get the world name
	 * 
	 * @return String with the world name
	 */
	public String getWorldName() {
		return WORLD_NAME;
	}

	/**
	 * Set the world
	 * 
	 * @param world
	 *            World which will be loaded for the vote
	 */
	public void setWorld(World world) {
		this.world = world;
	}

	/**
	 * Get the world
	 * 
	 * @return World with the information
	 */
	public World getWorld() {
		return world;
	}

}
