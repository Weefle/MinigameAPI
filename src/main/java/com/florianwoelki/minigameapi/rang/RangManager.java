package com.florianwoelki.minigameapi.rang;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;

import com.florianwoelki.minigameapi.Manager;
import com.florianwoelki.minigameapi.MinigameAPI;
import com.florianwoelki.minigameapi.database.DatabaseManager;

/**
 * The Class RangManager.
 */
public class RangManager extends Manager {

	/** The rangs. */
	private List<Rang> rangs;

	/** The database. */
	private String database;

	/** The database manager. */
	private DatabaseManager databaseManager;

	/**
	 * Instantiates a new rang manager.
	 *
	 * @param database
	 *            the database
	 */
	public RangManager(String database) {
		this.database = database;
		this.databaseManager = (DatabaseManager) MinigameAPI.getInstance().getManager("database");
	}

	@Override
	public void onLoad() {
		rangs = new ArrayList<>();
	}

	@Override
	public void onUnload() {
	}

	@Override
	public void onPlayerJoin(Player player) {
		int playerPoints = getPoints(player);
		Rang rang = getRangByPoints(playerPoints);

		rang.joinPlayer(player);
	}

	/**
	 * Gets the rang.
	 *
	 * @param player
	 *            the player
	 * @return the rang
	 */
	public Rang getRang(Player player) {
		for(Rang rang : rangs) {
			if(rang.getPlayers().contains(player)) {
				return rang;
			}
		}

		return null;
	}

	/**
	 * Gets the points.
	 *
	 * @param player
	 *            the player
	 * @return the points
	 */
	public int getPoints(Player player) {
		try {
			ResultSet resultSet = databaseManager.getDatabase().getDatabase().query("SELECT points FROM " + database + " WHERE uuid = " + player.getUniqueId().toString() + ";");
			while(resultSet.next()) {
				return resultSet.getInt("points");
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}

		return 0;
	}

	/**
	 * Gets the rang by points.
	 *
	 * @param points
	 *            the points
	 * @return the rang by points
	 */
	private Rang getRangByPoints(int points) {
		Rang rang = null;
		for(Rang currentRang : rangs) {
			if(points >= currentRang.getFromPoints() && points <= currentRang.getToPoints()) {
				rang = currentRang;
			}
		}

		return rang;
	}

	/**
	 * Gets the rangs.
	 *
	 * @return the rangs
	 */
	public List<Rang> getRangs() {
		return rangs;
	}

}
