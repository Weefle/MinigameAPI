package com.florianwoelki.minigameapi.rang;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;

/**
 * The Class Rang.
 */
public class Rang {

	/** The name. */
	private String name;

	/** The display name. */
	private String displayName;

	/** The from points. */
	private int fromPoints;

	/** The to points. */
	private int toPoints;

	/** The players. */
	private List<Player> players;

	/**
	 * Instantiates a new rang.
	 *
	 * @param name
	 *            the name
	 * @param displayName
	 *            the display name
	 * @param fromPoints
	 *            the from points
	 * @param toPoints
	 *            the to points
	 */
	public Rang(String name, String displayName, int fromPoints, int toPoints) {
		this.name = name;
		this.displayName = displayName;
		this.fromPoints = fromPoints;
		this.toPoints = toPoints;
		this.players = new ArrayList<>();
	}

	/**
	 * Join player.
	 *
	 * @param player
	 *            the player
	 */
	public void joinPlayer(Player player) {
		players.add(player);
	}

	/**
	 * Gets the from points.
	 *
	 * @return the from points
	 */
	public int getFromPoints() {
		return fromPoints;
	}

	/**
	 * Gets the to points.
	 *
	 * @return the to points
	 */
	public int getToPoints() {
		return toPoints;
	}

	/**
	 * Gets the players.
	 *
	 * @return the players
	 */
	public List<Player> getPlayers() {
		return players;
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
	 * Gets the display name.
	 *
	 * @return the display name
	 */
	public String getDisplayName() {
		return displayName;
	}

}
