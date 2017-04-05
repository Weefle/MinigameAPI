package com.florianwoelki.minigameapi.spectator;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import com.florianwoelki.minigameapi.MinigameAPI;
import com.florianwoelki.minigameapi.game.GameState;
import com.florianwoelki.minigameapi.messenger.MessageType;
import com.florianwoelki.minigameapi.messenger.Messenger;

public class SpectatorListener implements Listener {

	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onEntityDamage(EntityDamageEvent event) {
		if(event.getEntity().getType() != EntityType.PLAYER || !MinigameAPI.getInstance().getGame().isGameStarted()) {
			return;
		}

		Player player = (Player) event.getEntity();

		SpectatorManager.getSpectatorInventory().updateHealth(player, player.getHealth() - event.getFinalDamage());
	}

	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onEntityRegainHealth(EntityRegainHealthEvent event) {
		if(event.getEntity().getType() != EntityType.PLAYER || !MinigameAPI.getInstance().getGame().isGameStarted()) {
			return;
		}

		Player player = (Player) event.getEntity();

		SpectatorManager.getSpectatorInventory().updateHealth(player, player.getHealth() + event.getAmount());
	}

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		if(event.getAction().equals(Action.RIGHT_CLICK_BLOCK) || event.getAction().equals(Action.RIGHT_CLICK_AIR)) {
			if(event.getItem() == null) {
				return;
			}
			
			if(event.getItem().getType().equals(Material.COMPASS)) {
				event.getPlayer().openInventory(SpectatorManager.getSpectatorInventory().getInventory());
				SpectatorManager.getSpectatorInventory().updateInventory();
			}
		}
	}

	@EventHandler(priority = EventPriority.LOW)
	public void onInventoryClick(InventoryClickEvent event) {
		if(MinigameAPI.getInstance().getGame().getGameState() != GameState.INGAME || !(event.getWhoClicked() instanceof Player)) {
			return;
		}

		Player player = (Player) event.getWhoClicked();

		if(!SpectatorManager.isSpectator(player)) {
			return;
		}

		if(event.getClickedInventory() == null || !event.getClickedInventory().equals(SpectatorManager.getSpectatorInventory().getInventory())) {
			return;
		}

		if(event.getClick() != ClickType.LEFT && event.getClick() != ClickType.RIGHT) {
			return;
		}

		Player spectator = SpectatorManager.getSpectatorInventory().getPlayerFromSlot(event.getSlot());

		if(spectator == null) {
			return;
		}

		if(SpectatorManager.isSpectator(spectator) || !spectator.isOnline()) {
			SpectatorManager.getSpectatorInventory().updateInventory();
			return;
		}

		player.closeInventory();
		player.playSound(player.getLocation(), Sound.WOLF_SHAKE, 1.0F, 1.0F);
		player.teleport(spectator.getLocation());
		Messenger.getInstance().message(player, MessageType.INFO, "You have been teleported to §3" + ChatColor.stripColor(spectator.getDisplayName()) + "§7.");
		event.setCancelled(true);
	}

}
