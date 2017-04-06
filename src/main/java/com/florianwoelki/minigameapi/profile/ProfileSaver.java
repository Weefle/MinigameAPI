package com.florianwoelki.minigameapi.profile;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.bukkit.scheduler.BukkitRunnable;

import com.florianwoelki.minigameapi.MinigameAPI;
import com.florianwoelki.minigameapi.database.DatabaseManager;

public class ProfileSaver extends BukkitRunnable {

	private static final String SAVE = "UPDATE clients SET achievements = ? WHERE uuid = ?;";

	private Profile profile;
	private DatabaseManager databaseManager;

	public ProfileSaver(Profile profile) {
		this.profile = profile;
		this.databaseManager = (DatabaseManager) MinigameAPI.getInstance().getManager("database");
	}

	@Override
	public void run() {
		PreparedStatement preparedStatement = databaseManager.getDatabase().prepare(SAVE);
		try {
			preparedStatement.setString(1, new String(profile.getAchievements()));
			preparedStatement.setString(2, profile.getUuid().toString());
			preparedStatement.execute();
			preparedStatement.close();
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}

}
