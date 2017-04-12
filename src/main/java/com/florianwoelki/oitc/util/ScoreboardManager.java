package com.florianwoelki.oitc.util;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import com.florianwoelki.oitc.OITC;

public final class ScoreboardManager {

	private static Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();;

	public static void addKill(Player player) {
		OITC.getInstance().getPlayerKills().put(player, OITC.getInstance().getPlayerKills().get(player) + 1);

		for(Player online : Bukkit.getOnlinePlayers()) {
			updateScoreboard(online);
		}
	}

	public static void updateScoreboard(Player player) {
		Objective obj;
		if(board.getObjective("scoreboardK") == null)
			obj = board.registerNewObjective("scoreboardK", "dummyK");
		else
			obj = board.getObjective("scoreboardK");

		obj.setDisplaySlot(DisplaySlot.SIDEBAR);
		obj.setDisplayName("§3Player §8- §3Kills");
		for(Player onlinePlayer : OITC.getInstance().getPlayerKills().keySet()) {
			obj.getScore("§7" + onlinePlayer.getName()).setScore(OITC.getInstance().getPlayerKills().get(onlinePlayer));
		}

		player.setScoreboard(board);
	}

}
