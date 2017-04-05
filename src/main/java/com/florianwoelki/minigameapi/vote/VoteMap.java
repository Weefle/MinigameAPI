package com.florianwoelki.minigameapi.vote;

import org.bukkit.World;

public class VoteMap {

	private final String DISPLAY_NAME;
	private final String WORLD_NAME;

	private World world;

	public VoteMap(String displayName, String worldName) {
		this.DISPLAY_NAME = displayName;
		this.WORLD_NAME = worldName;
	}

	public String getDisplayName() {
		return DISPLAY_NAME;
	}

	public String getWorldName() {
		return WORLD_NAME;
	}

	public void setWorld(World world) {
		this.world = world;
	}

	public World getWorld() {
		return world;
	}

}
