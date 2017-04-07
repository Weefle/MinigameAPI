package com.florianwoelki.minigameapi.vote.event;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

import com.florianwoelki.minigameapi.vote.VoteMap;

/**
 * This event is called when a player votes for a map
 */
public class PlayerVoteForMapEvent extends PlayerEvent {

	private static final HandlerList HANDLERS = new HandlerList();

	private VoteMap votedMap;

	/**
	 * Constructor with the specific player and the map he voted for
	 * 
	 * @param player
	 *            Player who voted
	 * @param votedMap
	 *            VoteMap, the player has voted for
	 */
	public PlayerVoteForMapEvent(Player player, VoteMap votedMap) {
		super(player);
		this.votedMap = votedMap;
	}

	/**
	 * Set the voted map
	 * 
	 * @param votedMap
	 *            VoteMap which get set
	 */
	public void setVoteMap(VoteMap votedMap) {
		this.votedMap = votedMap;
	}

	/**
	 * Get the VoteMap
	 * 
	 * @return VoteMap, the player has voted for
	 */
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
