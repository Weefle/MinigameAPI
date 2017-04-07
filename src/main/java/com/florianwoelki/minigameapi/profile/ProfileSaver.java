package com.florianwoelki.minigameapi.profile;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.bukkit.scheduler.BukkitRunnable;

import com.florianwoelki.minigameapi.MinigameAPI;
import com.florianwoelki.minigameapi.database.DatabaseManager;

/**
 * The Class ProfileSaver.
 */
public class ProfileSaver extends BukkitRunnable {

	/** The Constant SAVE. */
	private static final String SAVE = "UPDATE clients SET achievements = ? WHERE uuid = ?;";

	/** The profile. */
	private Profile profile;

	/** The database manager. */
	private DatabaseManager databaseManager;

	/**
	 * Instantiates a new profile saver.
	 *
	 * @param profile
	 *            the profile
	 */
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
