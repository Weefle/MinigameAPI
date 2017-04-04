package com.florianwoelki.minigameapi.command.admin;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.florianwoelki.minigameapi.MinigameAPI;
import com.florianwoelki.minigameapi.command.Command;
import com.florianwoelki.minigameapi.game.GameState;
import com.florianwoelki.minigameapi.messenger.MessageType;
import com.florianwoelki.minigameapi.messenger.Messenger;

public class CommandStart implements CommandExecutor {

	@Override
	@Command(command = "start", permissions = "minigameapi.command.start")
	public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
		if(MinigameAPI.getInstance().getGame().getGameState() == GameState.LOBBY_WITH_NOY_PLAYERS) {
			MinigameAPI.getInstance().getGame().startLobbyPhase();
			Messenger.getInstance().message(sender, MessageType.GOOD, "You started the game countdown.");
		} else if(MinigameAPI.getInstance().getGame().getGameState() == GameState.LOBBY) {
			MinigameAPI.getInstance().getGame().startGame(true);
			Messenger.getInstance().message(sender, MessageType.GOOD, "You started the game.");
			for(Player onlinePlayer : Bukkit.getOnlinePlayers()) {
				onlinePlayer.setLevel(0);
				onlinePlayer.setExp(0);
			}
		}
		return false;
	}

}
