package com.florianwoelki.minigameapi.command.admin;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.florianwoelki.minigameapi.command.Command;
import com.florianwoelki.minigameapi.command.Sender;
import com.florianwoelki.minigameapi.location.LocationManager;
import com.florianwoelki.minigameapi.messenger.MessageType;
import com.florianwoelki.minigameapi.messenger.Messenger;

public class CommandSetGameArea implements CommandExecutor {

	private Map<Player, Location> position1 = new HashMap<>();
	private Map<Player, Location> position2 = new HashMap<>();

	@Override
	@Command(command = "setgamearea", permissions = "minigameapi.command.setgamearea", sender = Sender.PLAYER)
	public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
		Player player = (Player) sender;

		if(args.length == 0) {
			if(!position1.containsKey(player) || !position2.containsKey(player)) {
				Messenger.getInstance().message(player, MessageType.BAD, "First, set the positions with /setgamearea pos1 | pos2");
				return false;
			}

			Location pos1 = position1.get(player);
			Location pos2 = position2.get(player);

			LocationManager.getInstance().setBlockLocation(pos1, "GameArea.Pos1");
			LocationManager.getInstance().setBlockLocation(pos2, "GameArea.Pos2");
			Messenger.getInstance().message(player, MessageType.GOOD, "You have set the game area.");
			return false;
		}

		if(args[0].equalsIgnoreCase("pos1")) {
			position1.put(player, player.getLocation());
			Messenger.getInstance().message(player, MessageType.GOOD, "You have set the position 1 of the game area.");
		} else if(args[0].equalsIgnoreCase("pos2")) {
			position2.put(player, player.getLocation());
			Messenger.getInstance().message(player, MessageType.GOOD, "You have set the position 2 of the game area.");
		}
		return false;
	}

}
