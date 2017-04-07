package com.florianwoelki.minigameapi.team;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.scoreboard.Scoreboard;

import com.florianwoelki.minigameapi.Manager;
import com.florianwoelki.minigameapi.MinigameAPI;
import com.florianwoelki.minigameapi.team.event.PlayerSelectTeamEvent;

/**
 * The Class TeamScoreboardManager.
 */
public class TeamScoreboardManager extends Manager implements Listener {

	/** The scoreboard. */
	private Scoreboard scoreboard;

	@Override
	public void onLoad() {
		if(MinigameAPI.getInstance().getManager("teams") == null) {
			throw new RuntimeException("Team manager is required for this feature!");
		}

		scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
		for(Team team : ((TeamManager) MinigameAPI.getInstance().getManager("teams")).getTeams()) {
			org.bukkit.scoreboard.Team scoreboardTeam = scoreboard.registerNewTeam(team.getName());
			scoreboardTeam.setPrefix(team.getChatColor().toString());
			scoreboardTeam.setDisplayName(team.getChatColor() + team.getName());
		}

		Bukkit.getPluginManager().registerEvents(this, MinigameAPI.getInstance());
	}

	@Override
	public void onUnload() {
		HandlerList.unregisterAll(this);
	}

	/**
	 * Leave player.
	 *
	 * @param player
	 *            the player
	 */
	@SuppressWarnings("deprecation")
	public void leavePlayer(Player player) {
		org.bukkit.scoreboard.Team team = scoreboard.getPlayerTeam(player);

		if(team != null) {
			team.removePlayer(player);
		}
	}

	/**
	 * On player select team.
	 *
	 * @param event
	 *            the event
	 */
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onPlayerSelectTeam(PlayerSelectTeamEvent event) {
		Player player = event.getPlayer();
		org.bukkit.scoreboard.Team team = scoreboard.getPlayerTeam(player);

		if(team != null) {
			team.removePlayer(player);
		}

		scoreboard.getTeam(event.getTeam().getName()).addPlayer(player);
	}

	/**
	 * Gets the scoreboard.
	 *
	 * @return the scoreboard
	 */
	public Scoreboard getScoreboard() {
		return this.scoreboard;
	}
}
