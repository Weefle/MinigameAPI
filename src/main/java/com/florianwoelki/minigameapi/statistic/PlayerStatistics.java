package com.florianwoelki.minigameapi.statistic;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.entity.Player;

import com.florianwoelki.minigameapi.database.DatabaseManager;

/**
 * The Class PlayerStatistics.
 */
public class PlayerStatistics {

	/** The player. */
	private Player player;

	/** The database. */
	private String database;

	/** The database manager. */
	private DatabaseManager databaseManager;

	/**
	 * Instantiates a new player statistics.
	 *
	 * @param player
	 *            the player
	 * @param databaseManager
	 *            the database manager
	 * @param database
	 *            the database
	 */
	public PlayerStatistics(Player player, DatabaseManager databaseManager, String database) {
		this.player = player;
		this.database = database;
		this.databaseManager = databaseManager;
	}

	/**
	 * Sets the.
	 *
	 * @param <V>
	 *            the value type
	 * @param statisticObject
	 *            the statistic object
	 */
	public <V> void set(StatisticObject<V> statisticObject) {
		databaseManager.getDatabase().getDatabase().queryUpdate("UPDATE `" + database + "` SET `" + statisticObject.getName() + "` = '" + statisticObject.getValue() + "' WHERE `uuid` = '" + player.getUniqueId().toString() + "';");
	}

	/**
	 * Gets the object.
	 *
	 * @param name
	 *            the name
	 * @return the object
	 */
	public StatisticObject<Object> getObject(String name) {
		try {
			ResultSet resultSet = databaseManager.getDatabase().getDatabase().query("SELECT `" + name + "` FROM `" + database + "` WHERE `uuid` = '" + player.getUniqueId().toString() + "';");
			while(resultSet.next()) {
				Object object = resultSet.getObject(name);
				return new StatisticObject<Object>(name, object);
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}
