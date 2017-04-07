package com.florianwoelki.minigameapi.database.async;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.florianwoelki.minigameapi.database.Database;

/**
 * The Class AsyncDatabase.
 */
public class AsyncDatabase {

	private ExecutorService executor;
	private Database database;

	/**
	 * Instantiates a new async database.
	 */
	public AsyncDatabase() {
		this.executor = Executors.newCachedThreadPool();
		this.database = new Database();
	}

	/**
	 * Update.
	 *
	 * @param statement
	 *            the statement
	 */
	public void update(final PreparedStatement statement) {
		executor.execute(new Runnable() {
			@Override
			public void run() {
				database.queryUpdate(statement);
			}
		});
	}

	/**
	 * Update.
	 *
	 * @param statement
	 *            the statement
	 */
	public void update(final String statement) {
		executor.execute(new Runnable() {
			@Override
			public void run() {
				database.queryUpdate(statement);
			}
		});
	}

	/**
	 * Query.
	 *
	 * @param statement
	 *            the statement
	 * @param consumer
	 *            the consumer
	 */
	public void query(final PreparedStatement statement, final Consumer<ResultSet> consumer) {
		executor.execute(new Runnable() {
			@Override
			public void run() {
				consumer.accept(database.query(statement));
			}
		});
	}

	/**
	 * Query.
	 *
	 * @param statement
	 *            the statement
	 * @param consumer
	 *            the consumer
	 */
	public void query(final String statement, final Consumer<ResultSet> consumer) {
		executor.execute(new Runnable() {
			@Override
			public void run() {
				consumer.accept(database.query(statement));
			}
		});
	}

	/**
	 * Prepare.
	 *
	 * @param query
	 *            the query
	 * @return the prepared statement
	 */
	public PreparedStatement prepare(String query) {
		try {
			return database.getConnection().prepareStatement(query);
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Gets the database.
	 *
	 * @return the database
	 */
	public Database getDatabase() {
		return database;
	}

}
