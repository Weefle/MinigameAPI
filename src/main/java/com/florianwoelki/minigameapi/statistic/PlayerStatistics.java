package com.florianwoelki.minigameapi.statistic;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.entity.Player;

import com.florianwoelki.minigameapi.database.DatabaseManager;

public class PlayerStatistics {

	private Player player;

	private String database;
	private DatabaseManager databaseManager;

	public PlayerStatistics(Player player, DatabaseManager databaseManager, String database) {
		this.player = player;
		this.database = database;
		this.databaseManager = databaseManager;
	}

	public <V> void set(StatisticObject<V> statisticObject) {
		databaseManager.getDatabase().queryUpdate("UPDATE `" + database + "` SET `" + statisticObject.getName() + "` = '" + statisticObject.getValue() + "' WHERE `uuid` = '" + player.getUniqueId().toString() + "';");
	}

	public StatisticObject<Object> getObject(String name) {
		try {
			ResultSet resultSet = databaseManager.getDatabase().query("SELECT `" + name + "` FROM `" + database + "` WHERE `uuid` = '" + player.getUniqueId().toString() + "';");
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
