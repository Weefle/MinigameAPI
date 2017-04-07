package com.florianwoelki.minigameapi.spectator.event;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

/**
 * This event gets called when a player leaves the spectators.
 */
public class SpectatorLeaveEvent extends PlayerEvent {

	/** The Constant handlers. */
	private static final HandlerList handlers = new HandlerList();

	/**
	 * Instantiates a new spectator leave event.
	 *
	 * @param player
	 *            the player
	 */
	public SpectatorLeaveEvent(Player player) {
		super(player);
	}

	public HandlerList getHandlers() {
		return handlers;
	}

	/**
	 * Gets the handler list.
	 *
	 * @return the handler list
	 */
	public static HandlerList getHandlerList() {
		return handlers;
	}

}
