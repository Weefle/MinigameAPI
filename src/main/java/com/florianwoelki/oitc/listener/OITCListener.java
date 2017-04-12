package com.florianwoelki.oitc.listener;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import com.florianwoelki.minigameapi.MinigameAPI;
import com.florianwoelki.minigameapi.api.StopReason;
import com.florianwoelki.minigameapi.api.util.ItemBuilder;
import com.florianwoelki.minigameapi.location.LocationManager;
import com.florianwoelki.minigameapi.messenger.Messenger;
import com.florianwoelki.minigameapi.spectator.SpectatorManager;
import com.florianwoelki.oitc.OITC;
import com.florianwoelki.oitc.util.PlayerInventory;
import com.florianwoelki.oitc.util.ScoreboardManager;

public class OITCListener implements Listener {

	private Random random = new Random();

	@EventHandler
	public void onProjectileHit(ProjectileHitEvent event) {
		event.getEntity().remove();
	}

	@EventHandler
	public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
		if(event.getDamager() instanceof Arrow) {
			Player player = (Player) event.getEntity();
			Arrow arrow = (Arrow) event.getDamager();
			Player killer = (Player) arrow.getShooter();
			if(player instanceof Player && killer instanceof Player) {
				event.setDamage(40D);
			}
		}
	}

	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent event) {
		final Player player = event.getEntity();
		if(player.getKiller() instanceof Player) {
			final Player killer = player.getKiller();

			event.getDrops().clear();
			event.setDroppedExp(0);
			event.setDeathMessage(Messenger.getInstance().getPrefix() + "§3" + player.getName() + " §7has been killed.");

			killer.getInventory().addItem(new ItemBuilder(Material.ARROW).setName("Arrow").build());

			ScoreboardManager.addKill(killer);

			Bukkit.getScheduler().scheduleSyncDelayedTask(OITC.getInstance(), new Runnable() {
				@Override
				public void run() {
					player.spigot().respawn();
				}
			}, 2 * 20L);

			OITC.getInstance().getDeaths().put(player, OITC.getInstance().getDeaths().get(player) - 1);
			if(OITC.getInstance().getDeaths().get(player) < 1) {
				OITC.getInstance().getDeaths().remove(player);
				SpectatorManager.joinSpectator(player, false);
			}

			if(MinigameAPI.getInstance().getIngamePlayers().size() < 2) {
				MinigameAPI.getInstance().getGame().stopGame(StopReason.GAME_END);
			}
		}
	}

	@EventHandler
	public void onEntityDamage(EntityDamageEvent event) {
		if(event.getCause().equals(EntityDamageEvent.DamageCause.FALL)) {
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void onPlayerPickupItem(PlayerPickupItemEvent event) {
		event.setCancelled(true);
	}

	@EventHandler
	public void onCreatureSpawn(CreatureSpawnEvent event) {
		event.setCancelled(true);
	}

	@EventHandler
	public void onPlayerDropItem(PlayerDropItemEvent event) {
		event.setCancelled(true);
	}

	@EventHandler
	public void onPlayerRespawn(PlayerRespawnEvent event) {
		Player player = event.getPlayer();
		int randomSpawn = random.nextInt(LocationManager.getInstance().getConfiguration().getInt("Spawn.Counter")) + 1;

		if(OITC.getInstance().getDeaths().containsKey(player)) {
			event.setRespawnLocation(LocationManager.getInstance().getLocation("Spawn." + randomSpawn));
			PlayerInventory.givePlayerItems(player);
		}
	}

	@EventHandler
	public void onFoodLevelChange(FoodLevelChangeEvent event) {
		event.setCancelled(true);
	}

}
