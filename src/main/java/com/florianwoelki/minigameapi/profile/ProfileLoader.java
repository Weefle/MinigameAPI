package com.florianwoelki.minigameapi.profile;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.lang3.StringUtils;
import org.bukkit.scheduler.BukkitRunnable;

import com.florianwoelki.minigameapi.MinigameAPI;
import com.florianwoelki.minigameapi.achievement.AchievementManager;
import com.florianwoelki.minigameapi.database.DatabaseManager;

public class ProfileLoader extends BukkitRunnable {

	private static final String INSERT = "INSERT INTO clients VALUES (?, ?, ?) ON DUPLICATE KEY UPDATE name = ?;";
	private static final String SELECT = "SELECT achievements FROM clients WHERE uuid = ?;";

	private Profile profile;
	private DatabaseManager databaseManager;

	public ProfileLoader(Profile profile) {
		this.profile = profile;
		this.databaseManager = (DatabaseManager) MinigameAPI.getInstance().getManager("database");
	}

	@Override
	public void run() {
		try {
			PreparedStatement preparedStatement = databaseManager.getDatabase().prepare(INSERT);
			preparedStatement.setString(1, profile.getUuid().toString());
			preparedStatement.setString(2, profile.getName());
			preparedStatement.setString(3, StringUtils.repeat("n", ((AchievementManager) MinigameAPI.getInstance().getManager("achievement")).getAchievementList().size()));
			preparedStatement.setString(4, profile.getName());
			preparedStatement.execute();

			preparedStatement = databaseManager.getDatabase().prepare(SELECT);
			preparedStatement.setString(1, profile.getUuid().toString());

			ResultSet resultSet = preparedStatement.executeQuery();

			if(resultSet.next()) {
				profile.setAchievements(getAchievements(resultSet));
				profile.setLoaded(true);
			}

			preparedStatement.close();
			resultSet.close();
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}

	private char[] getAchievements(ResultSet resultSet) throws SQLException {
		char[] achieved = resultSet.getString("achievements").toCharArray();

		if(achieved.length == ((AchievementManager) MinigameAPI.getInstance().getManager("achievement")).getAchievementList().size()) {
			return achieved;
		}

		char[] adjusted = StringUtils.repeat("n", ((AchievementManager) MinigameAPI.getInstance().getManager("achievement")).getAchievementList().size()).toCharArray();
		System.arraycopy(achieved, 0, adjusted, 0, achieved.length);
		return adjusted;
	}

}
