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

public class SpectatorInventory {

	private final Inventory inv;
	private final Map<Integer, Player> players = new HashMap<>();

	public SpectatorInventory() {
		int slots = 9;
		while(slots < Bukkit.getMaxPlayers()) {
			slots += 9;
		}

		slots = Math.min(slots, 54);

		inv = Bukkit.createInventory(null, slots, "§8Spectator");
	}

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

	public Integer getSlotFromPlayer(Player player) {
		for(Map.Entry<Integer, Player> e : players.entrySet()) {
			if(e.getValue() == player) {
				return (Integer) e.getKey();
			}
		}

		return null;
	}

	public Player getPlayerFromSlot(int slot) {
		return (Player) players.get(Integer.valueOf(slot));
	}

	public Inventory getInventory() {
		return inv;
	}

}
