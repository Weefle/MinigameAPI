package com.florianwoelki.minigameapi.statistic;

import java.util.Map;

import org.bukkit.entity.Player;

import com.florianwoelki.minigameapi.MinigameAPI;
import com.florianwoelki.minigameapi.database.DatabaseManager;

/**
 * The Class StatisticManager.
 */
public class StatisticManager {

	/** The player statistics. */
	private Map<Player, PlayerStatistics> playerStatistics;

	/** The database. */
	private String database;

	/** The database manager. */
	private DatabaseManager databaseManager;

	/**
	 * Instantiates a new statistic manager.
	 *
	 * @param database
	 *            the database
	 */
	public StatisticManager(String database) {
		this.database = database;
		this.databaseManager = (DatabaseManager) MinigameAPI.getInstance().getManager("database");
	}

	/**
	 * Gets the player statistics.
	 *
	 * @param player
	 *            the player
	 * @return the player statistics
	 */
	public PlayerStatistics getPlayerStatistics(Player player) {
		if(playerStatistics.containsKey(player)) {
			return playerStatistics.get(player);
		}
		PlayerStatistics playerStatistic = new PlayerStatistics(player, databaseManager, database);
		playerStatistics.put(player, playerStatistic);
		return playerStatistic;
	}

}
