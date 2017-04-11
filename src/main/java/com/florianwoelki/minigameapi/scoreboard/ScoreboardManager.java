package com.florianwoelki.minigameapi.scoreboard;

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

	@Override
	public void onLoad() {
		scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
	}

	@Override
	public void onUnload() {
		scoreboard.clearSlot(DisplaySlot.BELOW_NAME);
		scoreboard.clearSlot(DisplaySlot.PLAYER_LIST);
		scoreboard.clearSlot(DisplaySlot.SIDEBAR);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onPlayerJoin(Player player) {
		player.setScoreboard(scoreboard);
		if(scoreboard.getPlayerTeam(player) != null) {
			scoreboard.getPlayerTeam(player).removePlayer(player);
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onPlayerQuit(Player player) {
		Team team = scoreboard.getPlayerTeam(player);
		team.removePlayer(player);
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
