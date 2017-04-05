package com.florianwoelki.minigameapi.vote.event;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

import com.florianwoelki.minigameapi.vote.VoteMap;

public class PlayerVoteForMapEvent extends PlayerEvent {

	private static final HandlerList HANDLERS = new HandlerList();

	private VoteMap votedMap;

	public PlayerVoteForMapEvent(Player player, VoteMap votedMap) {
		super(player);
		this.votedMap = votedMap;
	}

	public void setVotedMap(VoteMap votedMap) {
		this.votedMap = votedMap;
	}

	public VoteMap getVotedMap() {
		return votedMap;
	}

	@Override
	public HandlerList getHandlers() {
		return HANDLERS;
	}

	public static HandlerList getHandlerList() {
		return HANDLERS;
	}

}
