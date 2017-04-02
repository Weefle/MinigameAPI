package com.florianwoelki.minigameapi.database;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.entity.Player;

import com.florianwoelki.minigameapi.MinigameAPI;

public class PlayerStatistics {

	private Player player;

	private String database;
	private DatabaseManager databaseManager;

	public PlayerStatistics(Player player, String database) {
		this.player = player;
		this.database = database;
		this.databaseManager = (DatabaseManager) MinigameAPI.getInstance().getManager("database");
	}

	public void setObject(String objectName, Object object) {
		databaseManager.getDatabase().queryUpdate("UPDATE `" + database + "` SET `" + objectName + "` = '" + object + "' WHERE `uuid` = '" + player.getUniqueId().toString() + "';");
	}

	public Object getObject(String object) {
		ResultSet rs = databaseManager.getDatabase().query("SELECT `" + object + "` FROM `" + database + "` WHERE `uuid` = '" + player.getUniqueId().toString() + "';");
		try {
			while(rs.next()) {
				return rs.getObject(object);
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}
