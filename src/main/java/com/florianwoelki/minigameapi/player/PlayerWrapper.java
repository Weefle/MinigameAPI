package com.florianwoelki.minigameapi.player;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public abstract class PlayerWrapper implements Player {

	private Map<String, PlayerData<?>> playerMetadata;

	public PlayerWrapper() {
		this.playerMetadata = new HashMap<>();
	}

	public ItemStack getHead() {
		ItemStack itemSkull = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
		SkullMeta skullMeta = (SkullMeta) itemSkull.getItemMeta();

		skullMeta.setOwner(getName());
		itemSkull.setItemMeta(skullMeta);
		return itemSkull;
	}

	public void addMetadata(String key, PlayerData<?> value) {
		playerMetadata.put(key, value);
	}

	public Map<String, PlayerData<?>> getPlayerMetadata() {
		return playerMetadata;
	}

}
