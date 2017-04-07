package com.florianwoelki.minigameapi.spectator;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

import com.florianwoelki.minigameapi.MinigameAPI;
import com.florianwoelki.minigameapi.api.StopReason;
import com.florianwoelki.minigameapi.spectator.event.SpectatorJoinEvent;
import com.florianwoelki.minigameapi.spectator.event.SpectatorLeaveEvent;

/**
 * The Class SpectatorManager.
 */
public class SpectatorManager {

	/** The Constant SPECTATORS. */
	private static final List<Player> SPECTATORS = new ArrayList<>();

	/** The spectator inventory. */
	private static SpectatorInventory spectatorInventory = new SpectatorInventory();;

	/**
	 * Join spectator.
	 *
	 * @param player
	 *            the player
	 * @param isJoined
	 *            the is joined
	 */
	public static void joinSpectator(Player player, boolean isJoined) {
		if(SPECTATORS.contains(player)) {
			return;
		}

		SPECTATORS.add(player);
		player.spigot().setCollidesWithEntities(false);
		for(Player online : Bukkit.getOnlinePlayers()) {
			if(online != player) {
				online.hidePlayer(player);
			}
		}

		player.setGameMode(GameMode.ADVENTURE);
		player.setAllowFlight(true);
		spectatorInventory.updateInventory();

		if(player.getHealth() != 0.0D) {
			player.setFoodLevel(20);
			player.setHealth(20D);
		}

		MinigameAPI.getInstance().giveSpectatorItems(player, isJoined);

		if(MinigameAPI.getInstance().getIngamePlayers().size() <= 1) {
			MinigameAPI.getInstance().getGame().stopGame(StopReason.NO_PLAYERS);
		}

		Bukkit.getPluginManager().callEvent(new SpectatorJoinEvent(player));
	}

	/**
	 * Leave spectator.
	 *
	 * @param player
	 *            the player
	 */
	public static void leaveSpectator(Player player) {
		if(!SPECTATORS.contains(player)) {
			return;
		}

		spectatorInventory.updateInventory();
		SPECTATORS.remove(player);

		player.spigot().setCollidesWithEntities(true);
		for(Player online : Bukkit.getOnlinePlayers()) {
			if(online != player) {
				online.showPlayer(player);
			}
		}

		player.setFlying(false);
		player.setAllowFlight(false);
		player.setGameMode(GameMode.SURVIVAL);
		player.getInventory().clear();

		Bukkit.getPluginManager().callEvent(new SpectatorLeaveEvent(player));
	}

	/**
	 * Checks if is spectator.
	 *
	 * @param player
	 *            the player
	 * @return true, if is spectator
	 */
	public static boolean isSpectator(Player player) {
		return SPECTATORS.contains(player);
	}

	/**
	 * Gets the spectator inventory.
	 *
	 * @return the spectator inventory
	 */
	public static SpectatorInventory getSpectatorInventory() {
		return spectatorInventory;
	}

	/**
	 * Gets the spectators.
	 *
	 * @return the spectators
	 */
	public static List<Player> getSpectators() {
		return SPECTATORS;
	}

}
