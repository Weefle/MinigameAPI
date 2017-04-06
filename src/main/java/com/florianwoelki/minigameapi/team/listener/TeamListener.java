package com.florianwoelki.minigameapi.team.listener;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.projectiles.ProjectileSource;

import com.florianwoelki.minigameapi.MinigameAPI;
import com.florianwoelki.minigameapi.messenger.MessageType;
import com.florianwoelki.minigameapi.messenger.Messenger;
import com.florianwoelki.minigameapi.spectator.SpectatorManager;
import com.florianwoelki.minigameapi.spectator.event.SpectatorJoinEvent;
import com.florianwoelki.minigameapi.team.Team;
import com.florianwoelki.minigameapi.team.TeamManager;

public class TeamListener implements Listener {

	private final TeamManager manager;

	public TeamListener(TeamManager manager) {
		this.manager = manager;
	}

	@EventHandler
	public void onPlayerInteractAtEntity(PlayerInteractAtEntityEvent event) {
		if(event.getRightClicked().getType() != EntityType.ARMOR_STAND) {
			return;
		}

		Player player = event.getPlayer();
		ArmorStand armorStand = (ArmorStand) event.getRightClicked();

		Team team = manager.getTeamFromArmorStand(armorStand);

		if(team == null) {
			return;
		}

		event.setCancelled(true);

		if(MinigameAPI.getInstance().getGame().isGameStarted()) {
			Messenger.getInstance().message(player, MessageType.BAD, "The game already started!");
			return;
		}

		if(manager.getTeamFromPlayer(player) == team) {
			Messenger.getInstance().message(player, MessageType.BAD, "You are already in a team!");
			return;
		}

		if(manager.getPlayersInTeam(team).size() >= team.getTeamSize()) {
			Messenger.getInstance().message(player, MessageType.BAD, "This team is full.");
			return;
		}

		manager.setTeam(player, team);
		player.playSound(player.getLocation(), Sound.CLICK, 1.0F, 1.0F);
		Messenger.getInstance().message(player, MessageType.GOOD, "You are now in team " + team.getName());
	}

	@EventHandler(ignoreCancelled = true)
	public void onEntityDamage(EntityDamageByEntityEvent event) {
		if(event.getEntity().getType() != EntityType.PLAYER) {
			return;
		}

		Player player = (Player) event.getEntity();

		Player damager = null;

		if(event.getDamager().getType() == EntityType.PLAYER) {
			damager = (Player) event.getDamager();
		} else if(event.getDamager() instanceof Projectile) {
			ProjectileSource source = ((Projectile) event.getDamager()).getShooter();
			if(source instanceof Player) {
				damager = (Player) source;
			}
		}

		if(damager == null) {
			return;
		}

		if(manager.getTeamFromPlayer(player) == manager.getTeamFromPlayer(damager)) {
			event.setCancelled(true);
		}
	}

	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	public void onAsyncPlayerChat(AsyncPlayerChatEvent event) {
		Player player = event.getPlayer();
		Team team = manager.getTeamFromPlayer(player);

		if(team == null || SpectatorManager.isSpectator(player)) {
			return;
		}

		event.setFormat(team.getChatColor() + event.getFormat());

		if(MinigameAPI.getInstance().getGame().isGameStarted()) {
			if(event.getMessage().startsWith("#")) {
				event.setMessage(event.getMessage().substring(1));
				if(event.getMessage().trim().isEmpty()) {
					event.setCancelled(true);
					return;
				}
				event.setFormat(ChatColor.GOLD + "[GLOBAL] " + event.getFormat());
				return;
			}

			event.getRecipients().clear();
			for(Player p : team.getPlayers()) {
				event.getRecipients().add(p);
			}
		}
	}

	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	public void onEntityDamage(EntityDamageEvent event) {
		if(event.getEntity().getType() != EntityType.ARMOR_STAND) {
			return;
		}

		ArmorStand armorStand = (ArmorStand) event.getEntity();

		Team team = manager.getTeamFromArmorStand(armorStand);

		if(team != null) {
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void onSpectatorJoin(SpectatorJoinEvent event) {
		manager.leavePlayer(event.getPlayer());
	}

}
