package com.florianwoelki.minigameapi.command.user;

import org.bukkit.Sound;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.florianwoelki.minigameapi.MinigameAPI;
import com.florianwoelki.minigameapi.command.Command;
import com.florianwoelki.minigameapi.command.Sender;
import com.florianwoelki.minigameapi.game.GameState;
import com.florianwoelki.minigameapi.messenger.MessageType;
import com.florianwoelki.minigameapi.messenger.Messenger;
import com.florianwoelki.minigameapi.vote.VoteManager;
import com.florianwoelki.minigameapi.vote.VoteMap;

public class CommandVoteMap implements CommandExecutor {

	private VoteManager voteManager;

	public CommandVoteMap(VoteManager voteManager) {
		this.voteManager = voteManager;
	}

	@Override
	@Command(command = "votemap", alias = "vm", sender = Sender.PLAYER)
	public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
		Player player = (Player) sender;

		if(args.length == 0) {
			Messenger.getInstance().message(player, MessageType.BAD, "Please specify a map to vote.");
			return false;
		}

		if(MinigameAPI.getInstance().getGame().getGameState() != GameState.LOBBY || !voteManager.isVotingEnabled()) {
			Messenger.getInstance().message(player, MessageType.BAD, "You can't vote right now.");
			return false;
		}

		if(voteManager.hasVoted(player)) {
			Messenger.getInstance().message(player, MessageType.BAD, "You already voted for a map.");
			return false;
		}

		String votedMap = args[0];
		VoteMap map = voteManager.getMapByName(votedMap);

		if(map == null) {
			Messenger.getInstance().message(player, MessageType.BAD, "This map is not in the mappool.");
			return false;
		}

		player.playSound(player.getLocation(), Sound.CLICK, 1f, 1f);
		voteManager.voteMap(player, map);
		Messenger.getInstance().message(player, MessageType.GOOD, "You have voted for the map §3" + map.getDisplayName() + "§a.");
		return false;
	}

}
