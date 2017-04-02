package com.florianwoelki.minigameapi.statistic;

import java.util.Map;

import org.bukkit.entity.Player;

import com.florianwoelki.minigameapi.MinigameAPI;
import com.florianwoelki.minigameapi.database.DatabaseManager;

public class StatisticManager {

	private Map<Player, PlayerStatistics> playerStatistics;

	private String database;
	private DatabaseManager databaseManager;

	public StatisticManager(String database) {
		this.database = database;
		this.databaseManager = (DatabaseManager) MinigameAPI.getInstance().getManager("database");
	}

	public PlayerStatistics getPlayerStatistics(Player player) {
		if(playerStatistics.containsKey(player)) {
			return playerStatistics.get(player);
		}
		PlayerStatistics playerStatistic = new PlayerStatistics(player, databaseManager, database);
		playerStatistics.put(player, playerStatistic);
		return playerStatistic;
	}

}
