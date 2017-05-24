package com.florianwoelki.survivalgames.command;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.florianwoelki.minigameapi.command.Command;
import com.florianwoelki.minigameapi.command.Sender;
import com.florianwoelki.minigameapi.messenger.MessageType;
import com.florianwoelki.minigameapi.messenger.Messenger;
import com.florianwoelki.survivalgames.SurvivalGames;

public class CommandRefill implements CommandExecutor {

	@Override
	@Command(command = "refill", permissions = "SurvivalGames.Refill", sender = Sender.EVERYONE)
	public boolean onCommand(CommandSender sender, org.bukkit.command.Command cmd, String label, String[] args) {
		SurvivalGames.getInstance().getUsedChests().clear();
		Messenger.getInstance().broadcast(MessageType.GOOD, "All chests were refilled.");
		return false;
	}

}
