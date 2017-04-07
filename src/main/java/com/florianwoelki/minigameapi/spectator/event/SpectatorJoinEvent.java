package com.florianwoelki.minigameapi.spectator.event;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

/**
 * This event gets called when a player joins the spectators.
 */
public class SpectatorJoinEvent extends PlayerEvent {

	/** The Constant handlers. */
	private static final HandlerList handlers = new HandlerList();

	/**
	 * Instantiates a new spectator join event.
	 *
	 * @param player
	 *            the player
	 */
	public SpectatorJoinEvent(Player player) {
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
