package com.florianwoelki.minigameapi.kit;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.florianwoelki.minigameapi.Manager;
import com.florianwoelki.minigameapi.kit.event.PlayerGiveKitEvent;

public class KitManager extends Manager {

	private final List<Kit> kits = new ArrayList<>();

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

	public void giveKit(Player player, Kit kit) {
		kit.giveKit(player);
		Bukkit.getPluginManager().callEvent(new PlayerGiveKitEvent(player, kit));
	}

	public void addKit(Kit kit) {
		kits.add(kit);
	}

	public List<Kit> getKits() {
		return kits;
	}

}
