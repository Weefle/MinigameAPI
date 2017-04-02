package com.florianwoelki.minigameapi.achievement;

import java.util.LinkedList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import com.florianwoelki.minigameapi.api.util.ItemBuilder;

public class AchievementHandler {

	private List<Achievement> achievementList;

	public AchievementHandler() {
		this.achievementList = new LinkedList<>();
		for(AchievementType achievementType : AchievementType.values()) {
			achievementList.add(new Achievement(this, achievementType));
		}
	}

	public void register(Achievement... achievements) {
		for(Achievement achievement : achievements) {
			achievementList.add(achievement);
		}
	}

	public void openInventory(Player player) {
		Inventory inventory = Bukkit.createInventory(player, 27, "§c§lAchievements");

		for(Achievement achievement : achievementList) {
			if(achievement.hasAchievement(player)) {
				inventory.addItem(new ItemBuilder(Material.STAINED_CLAY, 1, (byte) 13).setName("§a" + achievement.getAchievementType().getName()).build());
			} else {
				inventory.addItem(new ItemBuilder(Material.STAINED_CLAY, 1, (byte) 14).setName("§c" + achievement.getAchievementType().getName()).build());
			}
		}

		player.openInventory(inventory);
	}

	public List<Achievement> getAchievementList() {
		return achievementList;
	}

}
