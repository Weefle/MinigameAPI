package com.florianwoelki.minigameapi.spectator;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import com.florianwoelki.minigameapi.MinigameAPI;
import com.florianwoelki.minigameapi.team.TeamManager;

/**
 * The Class SpectatorInventory.
 */
public class SpectatorInventory {

	/** The inv. */
	private final Inventory inv;

	/** The players. */
	private final Map<Integer, Player> players = new HashMap<>();

	/**
	 * Instantiates a new spectator inventory.
	 */
	public SpectatorInventory() {
		int slots = 9;
		while(slots < Bukkit.getMaxPlayers()) {
			slots += 9;
		}

		slots = Math.min(slots, 54);

		inv = Bukkit.createInventory(null, slots, "§8Spectator");
	}

	/**
	 * Update inventory.
	 */
	public void updateInventory() {
		players.clear();
		inv.clear();

		int slot = 0;
		for(Player player : MinigameAPI.getInstance().getIngamePlayers()) {
			if(slot >= inv.getSize()) {
				break;
			}

			ItemStack item = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
			SkullMeta skullMeta = (SkullMeta) item.getItemMeta();

			skullMeta.setOwner(player.getName());
			item.setItemMeta(skullMeta);

			ChatColor color = null;

			if(MinigameAPI.getInstance().getManager("teams") != null) {
				color = ((TeamManager) MinigameAPI.getInstance().getManager("teams")).getTeamFromPlayer(player).getChatColor();
			}

			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName((color != null ? color : "") + player.getDisplayName());
			meta.setLore(Arrays.asList(new String[] { getHealthString(player.getHealth()) }));
			item.setItemMeta(meta);

			inv.setItem(slot, item);
			players.put(Integer.valueOf(slot), player);
			slot++;
		}
	}

	/**
	 * Update health.
	 *
	 * @param player
	 *            the player
	 * @param health
	 *            the health
	 */
	public void updateHealth(Player player, double health) {
		Integer slot = getSlotFromPlayer(player);

		if(slot == null) {
			return;
		}

		ItemStack item = inv.getItem(slot.intValue());
		ItemMeta meta = item.getItemMeta();
		meta.setLore(Arrays.asList(new String[] { getHealthString(health) }));
		item.setItemMeta(meta);
		inv.setItem(slot.intValue(), item);
	}

	/**
	 * Gets the health string.
	 *
	 * @param health
	 *            the health
	 * @return the health string
	 */
	public String getHealthString(double health) {
		int h = (int) Math.round(health);
		h /= 2;
		int away = 10 - h;

		StringBuilder builder = new StringBuilder();
		for(int i = 0; i < h; i++) {
			builder.append(ChatColor.RED).append("❤");
		}
		for(int i = 0; i < away; i++) {
			builder.append(ChatColor.GRAY).append("❤");
		}

		return builder.toString();
	}

	/**
	 * Gets the slot from player.
	 *
	 * @param player
	 *            the player
	 * @return the slot from player
	 */
	public Integer getSlotFromPlayer(Player player) {
		for(Map.Entry<Integer, Player> e : players.entrySet()) {
			if(e.getValue() == player) {
				return (Integer) e.getKey();
			}
		}

		return null;
	}

	/**
	 * Gets the player from slot.
	 *
	 * @param slot
	 *            the slot
	 * @return the player from slot
	 */
	public Player getPlayerFromSlot(int slot) {
		return (Player) players.get(Integer.valueOf(slot));
	}

	/**
	 * Gets the inventory.
	 *
	 * @return the inventory
	 */
	public Inventory getInventory() {
		return inv;
	}

}
