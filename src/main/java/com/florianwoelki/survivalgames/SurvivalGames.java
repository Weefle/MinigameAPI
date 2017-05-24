package com.florianwoelki.survivalgames;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;

import com.florianwoelki.minigameapi.MinigameAPI;
import com.florianwoelki.minigameapi.achievement.Achievement;
import com.florianwoelki.minigameapi.achievement.AchievementManager;
import com.florianwoelki.minigameapi.api.Minigame;
import com.florianwoelki.minigameapi.api.StopReason;
import com.florianwoelki.survivalgames.command.CommandList;
import com.florianwoelki.survivalgames.command.CommandRefill;
import com.florianwoelki.survivalgames.listener.ChestListener;
import com.florianwoelki.survivalgames.listener.WorldListener;

public class SurvivalGames extends JavaPlugin implements Minigame {

	private static SurvivalGames instance;
	
	private final Map<String, Inventory> usedChests = new HashMap<>();
	
	private AchievementManager achievementManager;

	@Override
	public void onEnable() {
		instance = this;
		
		MinigameAPI.getInstance().initializeMinigame(this);
		MinigameAPI.getInstance().setMinigameName("SurvivalGames");
		MinigameAPI.getInstance().setChatPrefix("SurvivalGames");
		MinigameAPI.getInstance().setBlockChangesEnabled(true);
		MinigameAPI.getInstance().setAllowSpectatorDeath(true);
		MinigameAPI.getInstance().enableMapVoting();
		MinigameAPI.getInstance().enableEnchantHack();
		MinigameAPI.getInstance().enableDefaultScoreboard();

		MinigameAPI.getInstance().getCommandHandler().register(CommandList.class, new CommandList());
		MinigameAPI.getInstance().getCommandHandler().register(CommandRefill.class, new CommandRefill());

		Bukkit.getPluginManager().registerEvents(new WorldListener(), this);
		Bukkit.getPluginManager().registerEvents(new ChestListener(), this);

		achievementManager = (AchievementManager) MinigameAPI.getInstance().getManager("achievements");
		achievementManager.register(new Achievement(0, "Fighter", "Kill 9 players in one round"));
		achievementManager.register(new Achievement(1, "Sniper", "Kill a player with a bow with a distance of 50 blocks"));
		achievementManager.register(new Achievement(2, "First Win", "Win your first game"));
		achievementManager.register(new Achievement(3, "Fightroboter", "Kill 5000 players"));
		achievementManager.register(new Achievement(4, "The Best", "Die as the first one"));
		achievementManager.register(new Achievement(5, "Fisher", "Fish 5 fishes"));
		achievementManager.register(new Achievement(6, "Lucky One", "Survive a fight with one heart"));
		achievementManager.register(new Achievement(7, "Hot!", "Use 2 flint and steels"));
	}

	@Override
	public void startGame() {
	}

	@Override
	public void stopGame(StopReason stopReason) {
	}

	public AchievementManager getAchievementManager() {
		return achievementManager;
	}
	
	public Map<String, Inventory> getUsedChests() {
		return usedChests;
	}
	
	public static SurvivalGames getInstance() {
		return instance;
	}

}
