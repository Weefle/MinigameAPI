package com.florianwoelki.minigameapi.achievement;

import java.util.LinkedList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import com.florianwoelki.minigameapi.api.util.ItemBuilder;
import com.florianwoelki.minigameapi.client.Client;
import com.florianwoelki.minigameapi.client.ClientManager;

public class AchievementHandler {

	private static AchievementHandler instance;

	private List<Achievement> achievementList;

	public AchievementHandler() {
		this.achievementList = new LinkedList<>();
	}

	public void register(Achievement... achievements) {
		for(Achievement achievement : achievements) {
			achievementList.add(achievement);
		}
	}

	public void openInventory(Player player) {
		Client client = ClientManager.getInstance().getClient(player);
		Inventory inventory = Bukkit.createInventory(player, 27, "§c§lAchievements");

		for(Achievement achievement : achievementList) {
			if(client.hasAchievement(achievement)) {
				inventory.addItem(new ItemBuilder(Material.STAINED_CLAY, 1, (byte) 13).setName("§a" + achievement.getName()).build());
			} else {
				inventory.addItem(new ItemBuilder(Material.STAINED_CLAY, 1, (byte) 14).setName("§c" + achievement.getName()).build());
			}
		}

		player.openInventory(inventory);
	}

	public List<Achievement> getAchievementList() {
		return achievementList;
	}

	public static AchievementHandler getInstance() {
		if(instance == null) {
			instance = new AchievementHandler();
		}
		return instance;
	}

}
