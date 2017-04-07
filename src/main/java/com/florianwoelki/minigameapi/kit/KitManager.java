package com.florianwoelki.minigameapi.kit;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.florianwoelki.minigameapi.Manager;
import com.florianwoelki.minigameapi.kit.event.PlayerGiveKitEvent;

/**
 * The Class KitManager.
 * 
 * This needs to be registered in your main class.
 */
public class KitManager extends Manager {

	/** The kits. */
	private final List<Kit> kits = new ArrayList<>();

	/**
	 * Instantiates a new kit manager.
	 *
	 * @param kits
	 *            the kits
	 */
	public KitManager(Kit... kits) {
		for(Kit kit : kits) {
			this.kits.add(kit);
		}
	}

	@Override
	public void onLoad() {
	}

	@Override
	public void onUnload() {
	}

	/**
	 * Give kit.
	 *
	 * @param player
	 *            the player
	 * @param kit
	 *            the kit
	 */
	public void giveKit(Player player, Kit kit) {
		kit.giveKit(player);
		Bukkit.getPluginManager().callEvent(new PlayerGiveKitEvent(player, kit));
	}

	/**
	 * Adds the kit.
	 *
	 * @param kit
	 *            the kit
	 */
	public void addKit(Kit kit) {
		kits.add(kit);
	}

	/**
	 * Gets the kits.
	 *
	 * @return the kits
	 */
	public List<Kit> getKits() {
		return kits;
	}

}
