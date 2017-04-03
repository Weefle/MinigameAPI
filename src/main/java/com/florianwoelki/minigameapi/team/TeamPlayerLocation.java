package com.florianwoelki.minigameapi.team;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;

public class TeamPlayerLocation {

	private final Team team;
	private final double x;
	private final double y;
	private final double z;
	private BlockFace blockFace;
	private Player currentPlayer;

	public TeamPlayerLocation(Team team, double x, double y, double z, BlockFace blockFace) {
		this.team = team;
		this.x = x;
		this.y = y;
		this.z = z;
		this.blockFace = blockFace;
	}

	public Location getLocation() {
		return new Location((World) Bukkit.getWorlds().get(0), x, y + 0.001d, z, TeamManager.faceToYaw(blockFace), 0f);
	}

	public Team getTeam() {
		return team;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double getZ() {
		return z;
	}

	public BlockFace getBlockFace() {
		return blockFace;
	}

	public void setCurrentPlayer(Player currentPlayer) {
		this.currentPlayer = currentPlayer;
	}

	public Player getCurrentPlayer() {
		return currentPlayer;
	}

}
