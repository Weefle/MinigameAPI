package com.florianwoelki.minigameapi.tracker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.florianwoelki.minigameapi.MinigameAPI;
import com.florianwoelki.minigameapi.api.util.ItemBuilder;
import com.florianwoelki.minigameapi.messenger.MessageType;
import com.florianwoelki.minigameapi.messenger.Messenger;

/**
 * The Class TrackerItem.
 */
public class TrackerItem {

	/** The tracker. */
	private ItemStack tracker;
	
	/** The runnable. */
	private TrackerRunnable runnable;

	/** The trackers. */
	private final List<Player> trackers = new ArrayList<>();
	
	/** The player tracker. */
	private final Map<Player, Player> playerTracker = new HashMap<>();

	/** The max players to remove. */
	private int maxPlayersToRemove = -1;

	/**
	 * Gets the tracker.
	 *
	 * @return the tracker
	 */
	public ItemStack getTracker() {
		return tracker;
	}

	/**
	 * Checks if is tracker item.
	 *
	 * @param itemStack the item stack
	 * @return true, if is tracker item
	 */
	public boolean isTrackerItem(ItemStack itemStack) {
		if(itemStack == null || itemStack.getType() != Material.COMPASS || !itemStack.hasItemMeta()) {
			return false;
		}
		ItemMeta itemMeta = itemStack.getItemMeta();
		return itemMeta.getDisplayName() != null && itemMeta.getDisplayName().equalsIgnoreCase(getTracker().getItemMeta().getDisplayName());
	}

	/**
	 * Start tracker.
	 */
	public void startTracker() {
		runnable = new TrackerRunnable(this);
		runnable.start();
	}

	/**
	 * Stop tracker.
	 */
	public void stopTracker() {
		runnable.cancel();
	}

	/**
	 * Retrack.
	 *
	 * @param player the player
	 */
	public void retrack(Player player) {
		Player target = getNearestPlayer(player);
		if(trackers.contains(player)) {
			trackers.add(player);
		}

		playerTracker.put(player, target);
		if(target != null) {
			Messenger.getInstance().message(player, MessageType.INFO, "§7You are now tracking the player §a" + target.getDisplayName() + "§7!");
		}
	}

	/**
	 * Gets the nearest player.
	 *
	 * @param player the player
	 * @return the nearest player
	 */
	public Player getNearestPlayer(Player player) {
		double distance = Double.POSITIVE_INFINITY;
		Player target = null;
		for(Entity entity : player.getNearbyEntities(1000d, 1000d, 1000d)) {
			if(entity instanceof Player) {
				Player t = (Player) entity;
				if(!MinigameAPI.getInstance().getSpectators().contains(t) && TrackerRemover.getInstance().hasIgnored(player, t)) {
					double distanceTo = player.getLocation().distance(entity.getLocation());
					if(distanceTo < distance) {
						distance = distanceTo;
						target = t;
					}
				}
			}
		}
		return target;
	}

	/**
	 * Creates the tracker item.
	 */
	private void createTrackerItem() {
		ItemBuilder itemBuilder = new ItemBuilder(Material.COMPASS).setName("§6Tracker");
		itemBuilder.setLore("§r", "§7§mWith Rightclick", "§7§myou can remove players", "§7§mfrom your tracker.");

		tracker = itemBuilder.build();
	}

	/**
	 * Sets the max players to remove.
	 *
	 * @param maxPlayersToRemove the new max players to remove
	 */
	public void setMaxPlayersToRemove(int maxPlayersToRemove) {
		this.maxPlayersToRemove = maxPlayersToRemove;
		createTrackerItem();
	}

	/**
	 * Gets the max players to remove.
	 *
	 * @return the max players to remove
	 */
	public int getMaxPlayersToRemove() {
		return maxPlayersToRemove;
	}

	/**
	 * Gets the trackers.
	 *
	 * @return the trackers
	 */
	public List<Player> getTrackers() {
		return trackers;
	}

	/**
	 * Gets the player tracker.
	 *
	 * @return the player tracker
	 */
	public Map<Player, Player> getPlayerTracker() {
		return playerTracker;
	}

}
