package com.florianwoelki.minigameapi.client;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.entity.Player;

import com.florianwoelki.minigameapi.MinigameAPI;
import com.florianwoelki.minigameapi.database.DatabaseManager;
import com.florianwoelki.minigameapi.database.async.Consumer;
import com.florianwoelki.minigameapi.uuid.UUIDFetcher;

public class ClientManager {

	private static ClientManager instance;

	private final Map<String, Client> clients = new HashMap<>();
	private final DatabaseManager databaseManager = (DatabaseManager) MinigameAPI.getInstance().getManager("database");

	public Client getClient(UUID uuid) {
		return clients.get(UUIDFetcher.getName(uuid));
	}

	public Client getClient(String name) {
		return clients.get(name);
	}

	public Client getClient(Player player) {
		return clients.get(player.getName());
	}

	public Client addClient(final UUID uuid) {
		databaseManager.getDatabase().query("SELECT * FROM clients WHERE uuid = '" + uuid + "';", new Consumer<ResultSet>() {
			@Override
			public void accept(ResultSet resultSet) {
				try {
					while(resultSet.next()) {
						char[] achievements = resultSet.getString("achievements").toCharArray();
						clients.put(UUIDFetcher.getName(uuid), new Client(uuid, achievements));
					}

					if(!clients.containsKey(UUIDFetcher.getName(uuid))) {
						databaseManager.getDatabase().update("INSERT INTO clients (uuid, achievements) VALUES ('" + uuid + "', 'NULL');");
						clients.put(UUIDFetcher.getName(uuid), new Client(uuid));
					}
				} catch(SQLException e) {
					e.printStackTrace();
				}
			}
		});
		return getClient(uuid);
	}

	// TODO
	public Client getOfflineClient(UUID uuid) {
		return null;
	}

	public void removeClient(UUID uuid) {
		clients.remove(UUIDFetcher.getName(uuid));
	}

	public boolean isClientOnline(String name) {
		return clients.containsKey(name);
	}

	public static ClientManager getInstance() {
		if(instance == null) {
			instance = new ClientManager();
		}
		return instance;
	}

}
