package com.florianwoelki.minigameapi.profile;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.lang3.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import com.florianwoelki.minigameapi.MinigameAPI;
import com.florianwoelki.minigameapi.achievement.AchievementManager;
import com.florianwoelki.minigameapi.database.DatabaseManager;
import com.florianwoelki.minigameapi.profile.event.ProfileLoadedEvent;

/**
 * The Class ProfileLoader.
 */
public class ProfileLoader extends BukkitRunnable {

	/** The Constant INSERT. */
	private static final String INSERT = "INSERT INTO clients VALUES (?, ?, ?) ON DUPLICATE KEY UPDATE name = ?;";

	/** The Constant SELECT. */
	private static final String SELECT = "SELECT achievements FROM clients WHERE uuid = ?;";

	/** The profile. */
	private Profile profile;

	/** The database manager. */
	private DatabaseManager databaseManager;

	/**
	 * Instantiates a new profile loader.
	 *
	 * @param profile
	 *            the profile
	 */
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
			preparedStatement.setString(3, StringUtils.repeat("n", ((AchievementManager) MinigameAPI.getInstance().getManager("achievements")).getAchievementList().size()));
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

		if(profile.isLoaded()) {
			new BukkitRunnable() {
				@Override
				public void run() {
					Bukkit.getPluginManager().callEvent(new ProfileLoadedEvent(profile));
				}
			}.runTask(MinigameAPI.getInstance());
		}
	}

	/**
	 * Gets the achievements.
	 *
	 * @param resultSet
	 *            the result set
	 * @return the achievements
	 * @throws SQLException
	 *             the SQL exception
	 */
	private char[] getAchievements(ResultSet resultSet) throws SQLException {
		char[] achieved = resultSet.getString("achievements").toCharArray();

		if(achieved.length == ((AchievementManager) MinigameAPI.getInstance().getManager("achievements")).getAchievementList().size()) {
			return achieved;
		}

		char[] adjusted = StringUtils.repeat("n", ((AchievementManager) MinigameAPI.getInstance().getManager("achievements")).getAchievementList().size()).toCharArray();
		System.arraycopy(achieved, 0, adjusted, 0, achieved.length);
		return adjusted;
	}

}
