package com.florianwoelki.minigameapi.database;

import com.florianwoelki.minigameapi.Manager;

public class DatabaseManager extends Manager {

	private Database database;

	@Override
	public void load() {
		database = new Database();
		database.connect();
	}

	@Override
	public void unload() {
		database.disconnect();
	}

}
