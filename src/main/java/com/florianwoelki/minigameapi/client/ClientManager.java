package com.florianwoelki.minigameapi.client;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.entity.Player;

import com.florianwoelki.minigameapi.uuid.UUIDFetcher;

public class ClientManager {

	private static ClientManager instance;

	private final Map<String, Client> clients = new HashMap<>();

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
		return null;
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
