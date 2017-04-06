package com.florianwoelki.minigameapi.database.async;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.florianwoelki.minigameapi.database.Database;

public class AsyncDatabase {

	private ExecutorService executor;
	private Database database;

	public AsyncDatabase() {
		this.executor = Executors.newCachedThreadPool();
		this.database = new Database();
	}

	public void update(final PreparedStatement statement) {
		executor.execute(new Runnable() {
			@Override
			public void run() {
				database.queryUpdate(statement);
			}
		});
	}

	public void update(final String statement) {
		executor.execute(new Runnable() {
			@Override
			public void run() {
				database.queryUpdate(statement);
			}
		});
	}

	public void query(final PreparedStatement statement, final Consumer<ResultSet> consumer) {
		executor.execute(new Runnable() {
			@Override
			public void run() {
				consumer.accept(database.query(statement));
			}
		});
	}

	public void query(final String statement, final Consumer<ResultSet> consumer) {
		executor.execute(new Runnable() {
			@Override
			public void run() {
				consumer.accept(database.query(statement));
			}
		});
	}

	public PreparedStatement prepare(String query) {
		try {
			return database.getConnection().prepareStatement(query);
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public Database getDatabase() {
		return database;
	}

}
