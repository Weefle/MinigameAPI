package com.florianwoelki.survivalgames.command;

import java.util.List;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.florianwoelki.minigameapi.MinigameAPI;
import com.florianwoelki.minigameapi.command.Command;
import com.florianwoelki.minigameapi.command.Sender;
import com.florianwoelki.minigameapi.messenger.MessageType;
import com.florianwoelki.minigameapi.messenger.Messenger;

public class CommandList implements CommandExecutor {

	@Override
	@Command(command = "list", sender = Sender.EVERYONE)
	public boolean onCommand(CommandSender sender, org.bukkit.command.Command cmd, String label, String[] args) {
		StringBuilder builder = new StringBuilder();
		List<Player> players = MinigameAPI.getInstance().getIngamePlayers();
		for(int i = 0; i < players.size(); i++) {
			if(i != 0) {
				builder.append("§7, ");
			}
			builder.append("§a").append(players.get(i).getDisplayName());
		}
		Messenger.getInstance().message(sender, MessageType.INFO, "There are §a" + players.size() + " §7still alive. Players: " + builder.toString());
		
		builder = new StringBuilder();
		List<Player> spectators = MinigameAPI.getInstance().getSpectators();
		for(int i = 0; i < spectators.size(); i++) {
			if(i != 0) {
				builder.append("§7, ");
			}
			builder.append("§a").append(spectators.get(i).getDisplayName());
		}
		Messenger.getInstance().message(sender, MessageType.INFO, "There are §a" + spectators.size() + " §7spectators. Spectators: " + builder.toString());
		return false;
	}

}
