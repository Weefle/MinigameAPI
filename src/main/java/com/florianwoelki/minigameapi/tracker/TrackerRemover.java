package com.florianwoelki.minigameapi.tracker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.entity.Player;

public class TrackerRemover {

	private static TrackerRemover instance;

	private final Map<Player, List<Player>> players = new HashMap<>();

	public List<Player> getIgnoredPlayers(Player player) {
		if(players.containsKey(player)) {
			return players.get(player);
		}
		return new ArrayList<>();
	}

	public void ignorePlayer(TrackerItem trackerItem, Player player, Player toIgnore) {
		List<Player> hiddenPlayers = getIgnoredPlayers(player);
		hiddenPlayers.add(toIgnore);
		players.put(player, hiddenPlayers);
		if(trackerItem.getPlayerTracker().get(player) == toIgnore) {
			trackerItem.retrack(player);
		}
	}

	public void unignorePlayer(TrackerItem trackerItem, Player player, Player toShow) {
		List<Player> hiddenPlayers = getIgnoredPlayers(player);
		hiddenPlayers.remove(toShow);
		players.put(player, hiddenPlayers);
		trackerItem.retrack(player);
	}

	public boolean hasIgnored(Player player, Player ignore) {
		List<Player> hiddenPlayers = getIgnoredPlayers(player);
		return hiddenPlayers.contains(ignore);
	}

	public Map<Player, List<Player>> getPlayers() {
		return players;
	}

	public static TrackerRemover getInstance() {
		if(instance == null) {
			instance = new TrackerRemover();
		}
		return instance;
	}

}
