package com.florianwoelki.minigameapi.command.user;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.florianwoelki.minigameapi.achievement.AchievementManager;
import com.florianwoelki.minigameapi.command.Command;
import com.florianwoelki.minigameapi.command.Sender;

public class CommandAchievements implements CommandExecutor {

	private AchievementManager achievementManager;

	public CommandAchievements(AchievementManager achievementManager) {
		this.achievementManager = achievementManager;
	}

	@Override
	@Command(command = "achievements", sender = Sender.PLAYER)
	public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
		Player player = (Player) sender;

		achievementManager.openInventory(player);
		return false;
	}

}
