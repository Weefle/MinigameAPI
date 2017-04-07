package com.florianwoelki.minigameapi.vote.event;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.florianwoelki.minigameapi.vote.VoteMap;

/**
 * This event gets called when the voting has end
 */
public class VotingEndEvent extends Event {

	private static final HandlerList HANDLERS = new HandlerList();

	private VoteMap wonMap;

	/**
	 * Constructor with a specific VoteMap
	 * 
	 * @param wonMap
	 *            VoteMap, which won the vote
	 */
	public VotingEndEvent(VoteMap wonMap) {
		this.wonMap = wonMap;
	}

	/**
	 * Set the won map and force it
	 * 
	 * @param wonMap
	 *            VoteMap, which should win the vote
	 */
	public void setWonMap(VoteMap wonMap) {
		this.wonMap = wonMap;
	}

	/**
	 * Get the VoteMap that won the vote
	 * 
	 * @return VoteMap, which won the vote
	 */
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
