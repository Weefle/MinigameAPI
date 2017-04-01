package com.florianwoelki.minigameapi.command.admin;

import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.WorldCreator;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.florianwoelki.minigameapi.command.Command;
import com.florianwoelki.minigameapi.messenger.MessageType;
import com.florianwoelki.minigameapi.messenger.Messenger;

public class CommandWorldTeleport implements CommandExecutor {

	@Override
	@Command(command = "worldteleport", alias = "worldtp", permissions = "minigameapi.command.worldtp")
	public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
		Player player = (Player) sender;

		if(args.length == 0) {
			Messenger.getInstance().message(player, MessageType.BAD, "Please use: /worldteleport <worldname>");
			return false;
		}

		WorldCreator worldCreator = new WorldCreator(args[0]).environment(Environment.NORMAL).generateStructures(false).seed(0);
		World world = worldCreator.createWorld();

		player.teleport(world.getSpawnLocation());
		Messenger.getInstance().message(player, MessageType.GOOD, "You has been teleport to the world §c" + args[0] + MessageType.GOOD.getColor() + ".");
		return false;
	}

}
