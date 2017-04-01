package com.florianwoelki.minigameapi.listener;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityInteractEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;
import org.bukkit.event.hanging.HangingPlaceEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.inventory.ItemStack;

import com.florianwoelki.minigameapi.Manager;
import com.florianwoelki.minigameapi.MinigameAPI;
import com.florianwoelki.minigameapi.location.LocationManager;
import com.florianwoelki.minigameapi.messenger.Messenger;

public class LobbyListener implements Listener {

	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event) {
		if(event.getTo().getY() < 0) {
			event.getPlayer().teleport(LocationManager.getInstance().getLocation("Lobby"));
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();

		for(int i = 0; i < 60; i++) {
			player.sendMessage(" ");
		}

		player.teleport(LocationManager.getInstance().getLocation("Lobby"));

		player.setFoodLevel(20);
		player.setHealth(20d);
		player.setExp(0f);
		player.setLevel(0);
		player.setGameMode(GameMode.ADVENTURE);

		for(Manager manager : MinigameAPI.getInstance().getManagers()) {
			manager.onPlayerJoin(player);
		}

		event.setJoinMessage(Messenger.getInstance().getPrefix() + "§7" + player.getDisplayName() + " §ejoined the server. §8(§a" + Bukkit.getOnlinePlayers().size() + "§8/§a" + Bukkit.getMaxPlayers() + "§8)");
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		event.setQuitMessage(null);

		for(Manager manager : MinigameAPI.getInstance().getManagers()) {
			manager.onPlayerQuit(player);
		}
	}

	@EventHandler
	public void onPlayerKick(PlayerKickEvent event) {
		Player player = event.getPlayer();
		event.setLeaveMessage(null);

		for(Manager manager : MinigameAPI.getInstance().getManagers()) {
			manager.onPlayerQuit(player);
		}
	}

	@EventHandler
	public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
		if(!event.getRightClicked().getType().equals(EntityType.ITEM_FRAME)) {
			return;
		}

		Player player = event.getPlayer();

		if(player.getGameMode().equals(GameMode.CREATIVE)) {
			return;
		}
	}

	@EventHandler
	public void onHangingBreakByEntity(HangingBreakByEntityEvent event) {
		if(event.isCancelled() || !(event.getRemover() instanceof Player)) {
			return;
		}

		Player player = (Player) event.getRemover();

		if(player.getGameMode().equals(GameMode.CREATIVE)) {
			return;
		}
	}

	@EventHandler
	public void onHangingPlace(HangingPlaceEvent event) {
		Player player = event.getPlayer();

		if(player.getGameMode().equals(GameMode.CREATIVE)) {
			return;
		}
	}

	@EventHandler(priority = EventPriority.LOW)
	public void onPlayerDropItem(PlayerDropItemEvent event) {
		Player player = event.getPlayer();

		if(player.getGameMode().equals(GameMode.CREATIVE)) {
			return;
		}
	}

	@EventHandler(priority = EventPriority.LOW)
	public void onPlayerPickupItem(PlayerPickupItemEvent event) {
		Player player = event.getPlayer();

		if(player.getGameMode().equals(GameMode.CREATIVE)) {
			return;
		}
	}

	@EventHandler
	public void onEntityInteract(EntityInteractEvent event) {
		if(!MinigameAPI.getInstance().isBlockChangesEnabled() && !event.getEntityType().equals(EntityType.PLAYER) && event.getBlock().getType().equals(Material.SOIL)) {
			event.setCancelled(true);
		}
	}

	@EventHandler(priority = EventPriority.LOW)
	public void onFoodLevelChange(FoodLevelChangeEvent event) {
		if(event.isCancelled() || !(event.getEntity() instanceof Player)) {
			return;
		}

		Player player = (Player) event.getEntity();
	}

	@EventHandler
	public void onWeatherChange(WeatherChangeEvent event) {
		event.setCancelled(true);
	}

	@SuppressWarnings("incomplete-switch")
	@EventHandler
	public void onPlayerMoveItem(InventoryClickEvent event) {
		if(event.isCancelled() || !(event.getWhoClicked() instanceof Player)) {
			return;
		}

		Player player = (Player) event.getWhoClicked();

		if(player.getGameMode().equals(GameMode.CREATIVE)) {
			return;
		}

		switch(event.getAction()) {
		case DROP_ALL_CURSOR:
		case DROP_ALL_SLOT:
		case DROP_ONE_CURSOR:
		case DROP_ONE_SLOT:
			event.setCancelled(true);
			return;
		}

		event.setCancelled(true);
	}

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		ItemStack item = event.getItem();

		if(player.getGameMode().equals(GameMode.CREATIVE)) {
			return;
		}
	}

	@EventHandler
	public void noUproot(PlayerInteractEvent event) {
		if(event.getAction().equals(Action.PHYSICAL) && event.getClickedBlock().getType().equals(Material.SOIL)) {
			event.setCancelled(true);
		}
	}

}
