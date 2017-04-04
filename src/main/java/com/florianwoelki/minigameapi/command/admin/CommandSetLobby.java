package com.florianwoelki.minigameapi.command.admin;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.florianwoelki.minigameapi.command.Command;
import com.florianwoelki.minigameapi.command.Sender;
import com.florianwoelki.minigameapi.location.LocationManager;
import com.florianwoelki.minigameapi.messenger.MessageType;
import com.florianwoelki.minigameapi.messenger.Messenger;

public class CommandSetLobby implements CommandExecutor {

	@Override
	@Command(command = "setlobby", permissions = "minigameapi.command.setlobby", sender = Sender.PLAYER)
	public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
		Player player = (Player) sender;

		Messenger.getInstance().message(player, MessageType.GOOD, "You have set the lobby.");
		LocationManager.getInstance().setLocation(player.getLocation(), "Lobby");
		return false;
	}

}
