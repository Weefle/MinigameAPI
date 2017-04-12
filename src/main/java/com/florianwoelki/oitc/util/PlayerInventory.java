package com.florianwoelki.oitc.util;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import com.florianwoelki.minigameapi.api.util.ItemBuilder;
import com.florianwoelki.oitc.OITC;

public final class PlayerInventory {

	public static void givePlayerItems(Player player) {
		player.getInventory().setItem(0, new ItemBuilder(Material.WOOD_SWORD).setName("Wooden Sword").build());
		player.getInventory().setItem(1, new ItemBuilder(Material.BOW).setName("Bow").build());
		player.getInventory().addItem(new ItemBuilder(Material.ARROW).setName("Arrow").build());
		player.getInventory().setItem(8, new ItemBuilder(Material.REDSTONE, OITC.getInstance().getDeaths().get(player)).setName("Lifes").build());
	}

}
