package com.florianwoelki.minigameapi.vote.event;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.florianwoelki.minigameapi.vote.VoteMap;

public class VotingEndEvent extends Event {

	private static final HandlerList HANDLERS = new HandlerList();

	private VoteMap wonMap;

	public VotingEndEvent(VoteMap wonMap) {
		this.wonMap = wonMap;
	}

	public void setWonMap(VoteMap wonMap) {
		this.wonMap = wonMap;
	}

	public VoteMap getWonMap() {
		return wonMap;
	}

	@Override
	public HandlerList getHandlers() {
		return HANDLERS;
	}

	public static HandlerList getHandlerList() {
		return HANDLERS;
	}

}
