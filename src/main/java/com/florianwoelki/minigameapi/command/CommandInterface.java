package com.florianwoelki.minigameapi.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public interface CommandInterface {

	boolean onCommand(CommandSender commandSender, Command command, String cmdLabel, String[] args);
	
}
