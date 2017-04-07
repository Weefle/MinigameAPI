package com.florianwoelki.minigameapi.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.florianwoelki.minigameapi.config.ConfigData;

/**
 * The Class Database.
 */
public class Database {

	private final String HOST;
	private final int PORT;
	private final String DATABASE;
	private final String USERNAME;
	private final String PASSWORD;

	private Connection connection;

	/**
	 * Instantiates a new database.
	 */
	public Database() {
		this.HOST = ConfigData.host;
		this.PORT = ConfigData.port;
		this.DATABASE = ConfigData.database;
		this.USERNAME = ConfigData.username;
		this.PASSWORD = ConfigData.password;

		openConnection();
		queryUpdate("CREATE TABLE IF NOT EXISTS clients (uuid VARCHAR(36), name VARCHAR(16), achievements TEXT, PRIMARY KEY (uuid));");
	}

	/**
	 * Open connection.
	 *
	 * @return the connection
	 */
	public Connection openConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			return connection = DriverManager.getConnection("jdbc:mysql://" + HOST + ":" + PORT + "/" + DATABASE, USERNAME, PASSWORD);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Close connection.
	 */
	public void closeConnection() {
		try {
			connection.close();
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			connection = null;
		}
	}

	/**
	 * Query update.
	 *
	 * @param query
	 *            the query
	 */
	public void queryUpdate(String query) {
		checkConnection();
		try(PreparedStatement statement = connection.prepareStatement(query)) {
			queryUpdate(statement);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Query update.
	 *
	 * @param statement
	 *            the statement
	 */
	public void queryUpdate(PreparedStatement statement) {
		checkConnection();
		try {
			statement.executeUpdate();
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				statement.close();
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Query.
	 *
	 * @param query
	 *            the query
	 * @return the result set
	 */
	public ResultSet query(String query) {
		checkConnection();
		try {
			return query(connection.prepareStatement(query));
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Query.
	 *
	 * @param statement
	 *            the statement
	 * @return the result set
	 */
	public ResultSet query(PreparedStatement statement) {
		checkConnection();
		try {
			return statement.executeQuery();
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Check connection.
	 */
	private void checkConnection() {
		try {
			if(connection == null || !connection.isValid(10) || connection.isClosed()) {
				openConnection();
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Gets the connection.
	 *
	 * @return the connection
	 */
	public Connection getConnection() {
		return connection;
	}

}
