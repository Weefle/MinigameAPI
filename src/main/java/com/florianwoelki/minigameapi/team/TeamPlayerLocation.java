package com.florianwoelki.minigameapi.team;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;

/**
 * The Class TeamPlayerLocation.
 */
public class TeamPlayerLocation {

	/** The team. */
	private final Team team;

	/** The x. */
	private final double x;

	/** The y. */
	private final double y;

	/** The z. */
	private final double z;

	/** The block face. */
	private BlockFace blockFace;

	/** The current player. */
	private Player currentPlayer;

	/**
	 * Instantiates a new team player location.
	 *
	 * @param team
	 *            the team
	 * @param x
	 *            the x
	 * @param y
	 *            the y
	 * @param z
	 *            the z
	 * @param blockFace
	 *            the block face
	 */
	public TeamPlayerLocation(Team team, double x, double y, double z, BlockFace blockFace) {
		this.team = team;
		this.x = x;
		this.y = y;
		this.z = z;
		this.blockFace = blockFace;
	}

	/**
	 * Gets the location.
	 *
	 * @return the location
	 */
	public Location getLocation() {
		return new Location((World) Bukkit.getWorlds().get(0), x, y + 0.001d, z, TeamManager.faceToYaw(blockFace), 0f);
	}

	/**
	 * Gets the team.
	 *
	 * @return the team
	 */
	public Team getTeam() {
		return team;
	}

	/**
	 * Gets the x.
	 *
	 * @return the x
	 */
	public double getX() {
		return x;
	}

	/**
	 * Gets the y.
	 *
	 * @return the y
	 */
	public double getY() {
		return y;
	}

	/**
	 * Gets the z.
	 *
	 * @return the z
	 */
	public double getZ() {
		return z;
	}

	/**
	 * Gets the block face.
	 *
	 * @return the block face
	 */
	public BlockFace getBlockFace() {
		return blockFace;
	}

	/**
	 * Sets the current player.
	 *
	 * @param currentPlayer
	 *            the new current player
	 */
	public void setCurrentPlayer(Player currentPlayer) {
		this.currentPlayer = currentPlayer;
	}

	/**
	 * Gets the current player.
	 *
	 * @return the current player
	 */
	public Player getCurrentPlayer() {
		return currentPlayer;
	}

}
