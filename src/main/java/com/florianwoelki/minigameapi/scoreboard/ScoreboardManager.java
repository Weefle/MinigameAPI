package com.florianwoelki.minigameapi.scoreboard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import com.florianwoelki.minigameapi.Manager;

/**
 * The Class ScoreboardManager.
 */
public class ScoreboardManager extends Manager {

	/** The scoreboard. */
	private Scoreboard scoreboard;

	/** The groups. */
	private final LinkedList<ScoreboardGroup> groups = new LinkedList<>();
	
	/** The group cache. */
	private final Map<Player, ScoreboardGroup> groupCache = new HashMap<>();

	@Override
	public void onLoad() {
		scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();

		Iterator<Team> iterator = scoreboard.getTeams().iterator();
		while(iterator.hasNext()) {
			iterator.next().unregister();
		}
		for(ScoreboardGroup group : groups) {
			Team team = scoreboard.registerNewTeam("group_" + group.getId());
			team.setDisplayName("group_" + group.getDisplayName());
			team.setPrefix(group.getPrefix());
		}
	}

	@Override
	public void onUnload() {
		scoreboard.clearSlot(DisplaySlot.BELOW_NAME);
		scoreboard.clearSlot(DisplaySlot.PLAYER_LIST);
		scoreboard.clearSlot(DisplaySlot.SIDEBAR);

		List<Team> teams = new ArrayList<>();
		teams.addAll(scoreboard.getTeams());
		for(Team team : teams) {
			team.unregister();
		}
		groupCache.clear();
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onPlayerJoin(Player player) {
		player.setScoreboard(scoreboard);
		if(scoreboard.getPlayerTeam(player) != null) {
			scoreboard.getPlayerTeam(player).removePlayer(player);
		}

		ScoreboardGroup group = getScoreboardGroup(player);
		groupCache.put(player, group);
		Team team = scoreboard.getTeam("group_" + group.getId());
		if(team != null) {
			team.addPlayer(player);
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onPlayerQuit(Player player) {
		Team team = scoreboard.getPlayerTeam(player);
		team.removePlayer(player);
		groupCache.remove(player);
	}

	/**
	 * Gets the scoreboard group.
	 *
	 * @param player the player
	 * @return the scoreboard group
	 */
	public ScoreboardGroup getScoreboardGroup(Player player) {
		for(ScoreboardGroup group : groups) {
			if(player.hasPermission(group.getPermission())) {
				return group;
			}
		}

		return null;
	}

	/**
	 * Gets the cached scoreboard group.
	 *
	 * @param player the player
	 * @return the cached scoreboard group
	 */
	public ScoreboardGroup getCachedScoreboardGroup(Player player) {
		return groupCache.get(player);
	}

	/**
	 * Adds the scoreboard group.
	 *
	 * @param scoreboardGroup the scoreboard group
	 */
	public void addScoreboardGroup(ScoreboardGroup scoreboardGroup) {
		groups.add(scoreboardGroup);
	}

	/**
	 * Gets the groups.
	 *
	 * @return the groups
	 */
	public List<ScoreboardGroup> getGroups() {
		return groups;
	}

	/**
	 * Gets the scoreboard.
	 *
	 * @return the scoreboard
	 */
	public Scoreboard getScoreboard() {
		return scoreboard;
	}

}
