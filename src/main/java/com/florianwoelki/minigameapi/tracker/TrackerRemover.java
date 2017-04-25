package com.florianwoelki.minigameapi.tracker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.entity.Player;

/**
 * The Class TrackerRemover.
 */
public class TrackerRemover {

	/** The instance. */
	private static TrackerRemover instance;

	/** The players. */
	private final Map<Player, List<Player>> players = new HashMap<>();

	/**
	 * Gets the ignored players.
	 *
	 * @param player the player
	 * @return the ignored players
	 */
	public List<Player> getIgnoredPlayers(Player player) {
		if(players.containsKey(player)) {
			return players.get(player);
		}
		return new ArrayList<>();
	}

	/**
	 * Ignore player.
	 *
	 * @param trackerItem the tracker item
	 * @param player the player
	 * @param toIgnore the to ignore
	 */
	public void ignorePlayer(TrackerItem trackerItem, Player player, Player toIgnore) {
		List<Player> hiddenPlayers = getIgnoredPlayers(player);
		hiddenPlayers.add(toIgnore);
		players.put(player, hiddenPlayers);
		if(trackerItem.getPlayerTracker().get(player) == toIgnore) {
			trackerItem.retrack(player);
		}
	}

	/**
	 * Unignore player.
	 *
	 * @param trackerItem the tracker item
	 * @param player the player
	 * @param toShow the to show
	 */
	public void unignorePlayer(TrackerItem trackerItem, Player player, Player toShow) {
		List<Player> hiddenPlayers = getIgnoredPlayers(player);
		hiddenPlayers.remove(toShow);
		players.put(player, hiddenPlayers);
		trackerItem.retrack(player);
	}

	/**
	 * Checks for ignored.
	 *
	 * @param player the player
	 * @param ignore the ignore
	 * @return true, if successful
	 */
	public boolean hasIgnored(Player player, Player ignore) {
		List<Player> hiddenPlayers = getIgnoredPlayers(player);
		return hiddenPlayers.contains(ignore);
	}

	/**
	 * Gets the players.
	 *
	 * @return the players
	 */
	public Map<Player, List<Player>> getPlayers() {
		return players;
	}

	/**
	 * Gets the single instance of TrackerRemover.
	 *
	 * @return single instance of TrackerRemover
	 */
	public static TrackerRemover getInstance() {
		if(instance == null) {
			instance = new TrackerRemover();
		}
		return instance;
	}

}
