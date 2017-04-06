package com.florianwoelki.minigameapi.client.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.florianwoelki.minigameapi.client.Client;
import com.florianwoelki.minigameapi.client.ClientManager;
import com.florianwoelki.minigameapi.uuid.UUIDFetcher;

public class ClientListener implements Listener {

	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerLogin(PlayerLoginEvent event) {
		Player player = event.getPlayer();
		Client client = ClientManager.getInstance().addClient(UUIDFetcher.getUUID(player.getName()));
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		ClientManager.getInstance().removeClient(UUIDFetcher.getUUID(player.getName()));
	}

}
