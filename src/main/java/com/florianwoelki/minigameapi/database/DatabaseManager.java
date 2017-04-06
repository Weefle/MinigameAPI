package com.florianwoelki.minigameapi.database;

import com.florianwoelki.minigameapi.Manager;
import com.florianwoelki.minigameapi.database.async.AsyncDatabase;

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

	public AsyncDatabase getDatabase() {
		return database;
	}

}
