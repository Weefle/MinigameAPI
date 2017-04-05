package com.florianwoelki.minigameapi.vote;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.florianwoelki.minigameapi.MinigameAPI;
import com.florianwoelki.minigameapi.game.GameState;

public class VoteListener implements Listener {

	private final VoteManager manager;

	public VoteListener(VoteManager manager) {
		this.manager = manager;
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();

		if(MinigameAPI.getInstance().getGame().getGameState() == GameState.LOBBY && manager.isVotingEnabled()) {
			manager.sendVoting(player);
		}
	}

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		manager.removeVote(event.getPlayer());
	}

}
