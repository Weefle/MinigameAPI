package com.florianwoelki.minigameapi.listener;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.EntityInteractEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;
import org.bukkit.event.hanging.HangingPlaceEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.inventory.ItemStack;

import com.florianwoelki.minigameapi.Manager;
import com.florianwoelki.minigameapi.MinigameAPI;
import com.florianwoelki.minigameapi.achievement.AchievementManager;
import com.florianwoelki.minigameapi.api.StopReason;
import com.florianwoelki.minigameapi.config.ConfigData;
import com.florianwoelki.minigameapi.game.GameState;
import com.florianwoelki.minigameapi.location.LocationManager;
import com.florianwoelki.minigameapi.messenger.MessageType;
import com.florianwoelki.minigameapi.messenger.Messenger;
import com.florianwoelki.minigameapi.spectator.SpectatorManager;
import com.florianwoelki.minigameapi.util.ActionBarBroadcaster;

/**
 * The listener interface for receiving lobby events.
 * The class that is interested in processing a lobby
 * event implements this interface, and the object created
 * with that class is registered with a component using the
 * component's <code>addLobbyListener<code> method. When
 * the lobby event occurs, that object's appropriate
 * method is invoked.
 *
 * @see LobbyEvent
 */
public class LobbyListener implements Listener {

	/**
	 * On player move.
	 *
	 * @param event the event
	 */
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event) {
		if(event.getTo().getY() < 0) {
			if(LocationManager.getInstance().getLocation("Lobby") != null) {
				event.getPlayer().teleport(LocationManager.getInstance().getLocation("Lobby"));
			}
		}

		if(MinigameAPI.getInstance().getGame().isGameStarted()) {
			if(LocationManager.getInstance().getLocation("GameArea.Pos1") != null) {
				if(LocationManager.getInstance().isInsideLocation(event.getTo(), "GameArea")) {
					event.getPlayer().teleport(event.getFrom());
					Messenger.getInstance().message(event.getPlayer(), MessageType.BAD, "You can't leave the game area.");
				}
			}
		}
	}

	/**
	 * On player join.
	 *
	 * @param event the event
	 */
	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();

		for(int i = 0; i < 60; i++) {
			player.sendMessage(" ");
		}

		if(LocationManager.getInstance().getLocation("Lobby") != null) {
			player.teleport(LocationManager.getInstance().getLocation("Lobby"));
		}

		if(MinigameAPI.getInstance().getGame().getGameState() == GameState.INGAME) {
			for(Player spectator : SpectatorManager.getSpectators()) {
				player.hidePlayer(spectator);
			}

			event.setJoinMessage(null);
			SpectatorManager.joinSpectator(player, true);
			MinigameAPI.getInstance().giveLobbyItems(player);
			for(Manager manager : MinigameAPI.getInstance().getManagers()) {
				manager.onPlayerJoin(player);
			}
			return;
		}

		if(MinigameAPI.getInstance().getGame().getGameState() == GameState.LOBBY_WITH_NOY_PLAYERS) {
			if(Bukkit.getOnlinePlayers().size() >= ConfigData.minimumPlayers) {
				MinigameAPI.getInstance().getGame().startLobbyPhase();
			}

			MinigameAPI.getInstance().giveLobbyItems(player);
		} else {
			MinigameAPI.getInstance().giveLobbyItems(player);
		}

		player.setFoodLevel(20);
		player.setHealth(20d);
		player.setExp(0f);
		player.setLevel(0);
		player.setGameMode(GameMode.ADVENTURE);

		for(Manager manager : MinigameAPI.getInstance().getManagers()) {
			manager.onPlayerJoin(player);
		}

		new ActionBarBroadcaster(player).startBroadcast();

		event.setJoinMessage(Messenger.getInstance().getPrefix() + "§7" + player.getDisplayName() + " §ejoined the server. §8(§a" + Bukkit.getOnlinePlayers().size() + "§8/§a" + Bukkit.getMaxPlayers() + "§8)");
	}

	/**
	 * On player quit.
	 *
	 * @param event the event
	 */
	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		event.setQuitMessage(null);

		if(!MinigameAPI.getInstance().getGame().isGameStarted()) {
			if(MinigameAPI.getInstance().getGame().getGameState() != GameState.INGAME) {
				event.setQuitMessage(Messenger.getInstance().getPrefix() + "§7" + player.getDisplayName() + " §ehas left the server. §8(§a" + (Bukkit.getOnlinePlayers().size() - 1) + "§8/§a" + Bukkit.getMaxPlayers() + "§8)");
			} else {
				event.setQuitMessage(Messenger.getInstance().getPrefix() + "§7" + player.getDisplayName() + " §egave up.");
			}

			for(Manager manager : MinigameAPI.getInstance().getManagers()) {
				manager.onPlayerQuit(player);
			}
			return;
		}

		for(Manager manager : MinigameAPI.getInstance().getManagers()) {
			manager.onPlayerQuit(player);
		}

		if(SpectatorManager.isSpectator(player)) {
			SpectatorManager.leaveSpectator(player);
		}

		if(MinigameAPI.getInstance().getGame().isGameStarted() && MinigameAPI.getInstance().getIngamePlayers().size() < 2) {
			if(MinigameAPI.getInstance().getMinigame() != null) {
				MinigameAPI.getInstance().getMinigame().stopGame(StopReason.NO_PLAYERS);
			}
		}
	}

	/**
	 * On player kick.
	 *
	 * @param event the event
	 */
	@EventHandler
	public void onPlayerKick(PlayerKickEvent event) {
		event.setLeaveMessage(null);
	}

	/**
	 * On player respawn.
	 *
	 * @param event the event
	 */
	@EventHandler
	public void onPlayerRespawn(PlayerRespawnEvent event) {
		Player player = event.getPlayer();

		if(!MinigameAPI.getInstance().getGame().isGameStarted() || SpectatorManager.isSpectator(player)) {
			MinigameAPI.getInstance().giveLobbyItems(player);
		}

		if(MinigameAPI.getInstance().getGame().isGameStarted() && SpectatorManager.isSpectator(player) && MinigameAPI.getInstance().isAllowSpectatorDeath()) {
			player.setAllowFlight(true);
		}
	}

	/**
	 * On block break.
	 *
	 * @param event the event
	 */
	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		Player player = event.getPlayer();

		if(player.getGameMode() == GameMode.CREATIVE) {
			return;
		}

		if(SpectatorManager.isSpectator(player) || !MinigameAPI.getInstance().getGame().isGameStarted() || !MinigameAPI.getInstance().isBlockChangesEnabled()) {
			event.setCancelled(true);
		}
	}

	/**
	 * On block place.
	 *
	 * @param event the event
	 */
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent event) {
		Player player = event.getPlayer();

		if(player.getGameMode() == GameMode.CREATIVE) {
			return;
		}

		if(SpectatorManager.isSpectator(player) || !MinigameAPI.getInstance().getGame().isGameStarted() || !MinigameAPI.getInstance().isBlockChangesEnabled()) {
			event.setCancelled(true);
		}
	}

	/**
	 * On entity explode.
	 *
	 * @param event the event
	 */
	@EventHandler
	public void onEntityExplode(EntityExplodeEvent event) {
		if(!MinigameAPI.getInstance().getGame().isGameStarted() || !MinigameAPI.getInstance().isBlockChangesEnabled()) {
			event.setCancelled(true);
		}
	}

	/**
	 * On player interact entity.
	 *
	 * @param event the event
	 */
	@EventHandler
	public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
		if(!event.getRightClicked().getType().equals(EntityType.ITEM_FRAME)) {
			return;
		}

		Player player = event.getPlayer();

		if(player.getGameMode().equals(GameMode.CREATIVE)) {
			return;
		}

		if(SpectatorManager.isSpectator(player) || !MinigameAPI.getInstance().getGame().isGameStarted() || !MinigameAPI.getInstance().isBlockChangesEnabled()) {
			event.setCancelled(true);
		}
	}

	/**
	 * On hanging break by entity.
	 *
	 * @param event the event
	 */
	@EventHandler
	public void onHangingBreakByEntity(HangingBreakByEntityEvent event) {
		if(event.isCancelled() || !(event.getRemover() instanceof Player)) {
			return;
		}

		Player player = (Player) event.getRemover();

		if(player.getGameMode().equals(GameMode.CREATIVE)) {
			return;
		}

		if(SpectatorManager.isSpectator(player) || !MinigameAPI.getInstance().getGame().isGameStarted() || !MinigameAPI.getInstance().isBlockChangesEnabled()) {
			event.setCancelled(true);
		}
	}

	/**
	 * On hanging place.
	 *
	 * @param event the event
	 */
	@EventHandler
	public void onHangingPlace(HangingPlaceEvent event) {
		Player player = event.getPlayer();

		if(player.getGameMode().equals(GameMode.CREATIVE)) {
			return;
		}

		if(SpectatorManager.isSpectator(player) || !MinigameAPI.getInstance().getGame().isGameStarted() || !MinigameAPI.getInstance().isBlockChangesEnabled()) {
			event.setCancelled(true);
		}
	}

	/**
	 * On teleport.
	 *
	 * @param event the event
	 */
	@EventHandler
	public void onTeleport(PlayerTeleportEvent event) {
		if(SpectatorManager.isSpectator(event.getPlayer())) {
			event.getPlayer().setAllowFlight(true);
		}
	}

	/**
	 * On world change.
	 *
	 * @param event the event
	 */
	@EventHandler
	public void onWorldChange(PlayerChangedWorldEvent event) {
		if(SpectatorManager.isSpectator(event.getPlayer())) {
			event.getPlayer().setAllowFlight(true);
		}
	}

	/**
	 * On entity damage.
	 *
	 * @param event the event
	 */
	@EventHandler(priority = EventPriority.LOW)
	public void onEntityDamage(EntityDamageEvent event) {
		if(event.isCancelled()) {
			return;
		}

		if(!MinigameAPI.getInstance().getGame().isGameStarted()) {
			event.setCancelled(true);
			return;
		}

		if(event.getEntity() instanceof Player) {
			Player player = (Player) event.getEntity();
			if(SpectatorManager.isSpectator(player)) {
				event.setCancelled(true);
				player.setFireTicks(0);
				return;
			}
		}

		if(event instanceof EntityDamageByEntityEvent) {
			EntityDamageByEntityEvent event2 = (EntityDamageByEntityEvent) event;
			if(event2.getDamager() instanceof Player) {
				Player damager = (Player) event2.getDamager();
				if(SpectatorManager.isSpectator(damager)) {
					damager.setFireTicks(0);
					event.setCancelled(true);
				}
			}
		}
	}

	/**
	 * On player drop item.
	 *
	 * @param event the event
	 */
	@EventHandler(priority = EventPriority.LOW)
	public void onPlayerDropItem(PlayerDropItemEvent event) {
		Player player = event.getPlayer();

		if(player.getGameMode().equals(GameMode.CREATIVE)) {
			return;
		}

		if(!MinigameAPI.getInstance().getGame().isGameStarted() || SpectatorManager.isSpectator(player)) {
			event.setCancelled(true);
		}
	}

	/**
	 * On player pickup item.
	 *
	 * @param event the event
	 */
	@EventHandler(priority = EventPriority.LOW)
	public void onPlayerPickupItem(PlayerPickupItemEvent event) {
		Player player = event.getPlayer();

		if(player.getGameMode().equals(GameMode.CREATIVE)) {
			return;
		}

		if(!MinigameAPI.getInstance().getGame().isGameStarted() || SpectatorManager.isSpectator(player)) {
			event.setCancelled(true);
		}
	}

	/**
	 * On entity interact.
	 *
	 * @param event the event
	 */
	@EventHandler
	public void onEntityInteract(EntityInteractEvent event) {
		if(!MinigameAPI.getInstance().isBlockChangesEnabled() && !event.getEntityType().equals(EntityType.PLAYER) && event.getBlock().getType().equals(Material.SOIL)) {
			event.setCancelled(true);
		}
	}

	/**
	 * On food level change.
	 *
	 * @param event the event
	 */
	@EventHandler(priority = EventPriority.LOW)
	public void onFoodLevelChange(FoodLevelChangeEvent event) {
		if(event.isCancelled() || !(event.getEntity() instanceof Player)) {
			return;
		}

		Player player = (Player) event.getEntity();

		if(!MinigameAPI.getInstance().getGame().isGameStarted() || SpectatorManager.isSpectator(player)) {
			event.setCancelled(true);
		}
	}

	/**
	 * On weather change.
	 *
	 * @param event the event
	 */
	@EventHandler
	public void onWeatherChange(WeatherChangeEvent event) {
		event.setCancelled(true);
	}

	/**
	 * On player move item.
	 *
	 * @param event the event
	 */
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

		if(MinigameAPI.getInstance().getGame().isGameStarted() && SpectatorManager.isSpectator(player)) {
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

	/**
	 * On player interact lobby items.
	 *
	 * @param event the event
	 */
	@EventHandler
	public void onPlayerInteractLobbyItems(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		ItemStack item = event.getItem();

		if(player.getGameMode() == GameMode.CREATIVE) {
			return;
		}

		if(event.getAction() != Action.PHYSICAL && item != null && !MinigameAPI.getInstance().getGame().isGameStarted()) {
			if(item.getType() == Material.MAGMA_CREAM) {
				player.kickPlayer("");
			} else if(item.getType() == Material.WRITTEN_BOOK) {
				((AchievementManager) MinigameAPI.getInstance().getManager("achievements")).openInventory(player);
				player.playSound(player.getLocation(), Sound.CHEST_OPEN, 1f, 1f);
			}
		}
	}

	/**
	 * On player interact.
	 *
	 * @param event the event
	 */
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		ItemStack item = event.getItem();

		if(player.getGameMode().equals(GameMode.CREATIVE)) {
			return;
		}

		if(event.getAction() != Action.PHYSICAL && item != null && (MinigameAPI.getInstance().getGame().isGameStarted() || SpectatorManager.isSpectator(player))) {
			event.setCancelled(true);
			if(item.getType() == Material.MAGMA_CREAM) {
				player.kickPlayer("");
			} else if(item.getType() == Material.COMPASS && MinigameAPI.getInstance().getGame().getGameState() == GameState.INGAME) {
				player.openInventory(SpectatorManager.getSpectatorInventory().getInventory());
			} else {
				event.setCancelled(false);
			}
		}

		if(SpectatorManager.isSpectator(player)) {
			event.setCancelled(true);
		}
	}

	/**
	 * No uproot.
	 *
	 * @param event the event
	 */
	@EventHandler
	public void noUproot(PlayerInteractEvent event) {
		if(event.getAction().equals(Action.PHYSICAL) && event.getClickedBlock().getType().equals(Material.SOIL)) {
			event.setCancelled(true);
		}
	}

}
