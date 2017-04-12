package com.florianwoelki.oitc.command;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.florianwoelki.minigameapi.command.Command;
import com.florianwoelki.minigameapi.command.Sender;
import com.florianwoelki.minigameapi.location.LocationManager;
import com.florianwoelki.minigameapi.messenger.MessageType;
import com.florianwoelki.minigameapi.messenger.Messenger;

public class CommandSetSpawn implements CommandExecutor {

	@Override
	@Command(command = "setspawn", sender = Sender.PLAYER, permissions = "OITC.SetSpawn")
	public boolean onCommand(CommandSender sender, org.bukkit.command.Command cmd, String label, String[] args) {
		Player player = (Player) sender;

		int counter = LocationManager.getInstance().getConfiguration().getInt("Spawn.Counter");
		counter++;
		LocationManager.getInstance().getConfiguration().set("Spawn.Counter", counter);
		LocationManager.getInstance().setLocation(player.getLocation(), "Spawn." + counter);
		Messenger.getInstance().message(player, MessageType.GOOD, "You have set the spawn §6" + counter + "§a.");
		return false;
	}

}
