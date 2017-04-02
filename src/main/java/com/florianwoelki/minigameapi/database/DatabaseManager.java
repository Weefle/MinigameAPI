package com.florianwoelki.minigameapi.database;

import com.florianwoelki.minigameapi.Manager;

public class DatabaseManager extends Manager {

	private Database database;

	@Override
	public void onLoad() {
		database = new Database();
	}

	@Override
	public void onUnload() {
		database.closeConnection();
	}

	public Database getDatabase() {
		return database;
	}

}
