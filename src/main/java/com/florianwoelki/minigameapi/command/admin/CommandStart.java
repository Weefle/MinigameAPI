package com.florianwoelki.minigameapi.command.admin;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.florianwoelki.minigameapi.command.Command;

public class CommandStart implements CommandExecutor {

	@Override
	@Command(command = "start", permissions = "minigameapi.command.start")
	public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {

		return false;
	}

}
