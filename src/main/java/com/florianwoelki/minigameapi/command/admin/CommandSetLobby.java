package com.florianwoelki.minigameapi.command.admin;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.florianwoelki.minigameapi.command.Command;
import com.florianwoelki.minigameapi.command.Sender;

public class CommandSetLobby implements CommandExecutor {

	@Override
	@Command(command = "setlobby", permissions = "minigameapi.command.setlobby", sender = Sender.PLAYER)
	public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {

		return false;
	}

}
