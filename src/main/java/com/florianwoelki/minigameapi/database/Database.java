package com.florianwoelki.minigameapi.database;

import java.sql.Connection;
import java.sql.SQLException;

public class Database {

	private final String HOST;
	private final int PORT;
	private final String DATABASE;
	private final String USERNAME;
	private final String PASSWORD;

	private Connection connection;

	public Database() {
		HOST = "";
		PORT = 3306;
		DATABASE = "";
		USERNAME = "";
		PASSWORD = "";
	}

	public void connect() {

	}

	public void disconnect() {

	}

	public boolean isConnected() {
		try {
			return connection != null && connection.isValid(10) && !connection.isClosed();
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public Connection getConnection() {
		return connection;
	}

}
