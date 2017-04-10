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

/**
 * This class represents a AchievementManager and it extends from the Manager class.
 * 
 * This class needs to be enabled in your main class.
 */
public class AchievementManager extends Manager {

	private List<Achievement> achievementList;

	@Override
	public void onLoad() {
		this.achievementList = new LinkedList<>();
	}

	@Override
	public void onUnload() {
	}

	/**
	 * Register some achievements.
	 *
	 * @param achievements
	 *            the achievements
	 */
	public void register(Achievement... achievements) {
		for(Achievement achievement : achievements) {
			achievementList.add(achievement);
		}
	}

	/**
	 * Open inventory with all given achievements.
	 *
	 * @param player
	 *            the player
	 */
	public void openInventory(Player player) {
		Profile profile = ((ProfileManager) MinigameAPI.getInstance().getManager("profile")).getProfile(player);
		Inventory inventory = Bukkit.createInventory(player, 27, "§c§lAchievements");

		// Construct the inventory
		for(Achievement achievement : achievementList) {
			if(profile.hasAchievement(achievement)) {
				inventory.addItem(new ItemBuilder(Material.STAINED_CLAY, 1, (byte) 13).setName("§a" + achievement.getName()).setLore("§r", "§bDescription", "§7" + achievement.getDescription()).build()); // Green Clay
			} else {
				inventory.addItem(new ItemBuilder(Material.STAINED_CLAY, 1, (byte) 14).setName("§c" + achievement.getName()).setLore("§r", "§bDescription", "§7" + achievement.getDescription()).build()); // Red Clay
			}
		}

		player.openInventory(inventory);
	}

	/**
	 * Gets the achievement list.
	 *
	 * @return the achievement list
	 */
	public List<Achievement> getAchievementList() {
		return achievementList;
	}

}
