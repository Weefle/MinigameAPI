package com.florianwoelki.minigameapi.database;

import com.florianwoelki.minigameapi.Manager;
import com.florianwoelki.minigameapi.database.async.AsyncDatabase;

/**
 * The Class DatabaseManager.
 * 
 * This class needs to be registered in your main class.
 */
public class DatabaseManager extends Manager {

	private AsyncDatabase database;

	@Override
	public void onLoad() {
		database = new AsyncDatabase();
	}

	@Override
	public void onUnload() {
		database.getDatabase().closeConnection();
	}

	/**
	 * Gets the database.
	 *
	 * @return the database
	 */
	public AsyncDatabase getDatabase() {
		return database;
	}

}
