package com.florianwoelki.minigameapi.achievement;

import java.util.LinkedList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import com.florianwoelki.minigameapi.Manager;
import com.florianwoelki.minigameapi.MinigameAPI;
import com.florianwoelki.minigameapi.api.util.ItemBuilder;
import com.florianwoelki.minigameapi.profile.Profile;
import com.florianwoelki.minigameapi.profile.ProfileManager;

public class AchievementManager extends Manager {

	private List<Achievement> achievementList;

	@Override
	public void onLoad() {
		this.achievementList = new LinkedList<>();
	}

	@Override
	public void onUnload() {
	}

	public void register(Achievement... achievements) {
		for(Achievement achievement : achievements) {
			achievementList.add(achievement);
		}
	}

	public void openInventory(Player player) {
		Profile profile = ((ProfileManager) MinigameAPI.getInstance().getManager("profile")).getProfile(player);
		Inventory inventory = Bukkit.createInventory(player, 27, "§c§lAchievements");

		for(Achievement achievement : achievementList) {
			if(profile.hasAchievement(achievement)) {
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

}
