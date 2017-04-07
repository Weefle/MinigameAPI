package com.florianwoelki.minigameapi.kit.event;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

import com.florianwoelki.minigameapi.kit.Kit;

/**
 * This event gets called when a player receives a kit.
 */
public class PlayerGiveKitEvent extends PlayerEvent {

	/** The handlers. */
	private static HandlerList handlers = new HandlerList();

	/** The kit. */
	private Kit kit;

	/**
	 * Instantiates a new player give kit event.
	 *
	 * @param player
	 *            the player
	 * @param kit
	 *            the kit
	 */
	public PlayerGiveKitEvent(Player player, Kit kit) {
		super(player);
		this.kit = kit;
	}

	/**
	 * Sets the kit.
	 *
	 * @param kit
	 *            the new kit
	 */
	public void setKit(Kit kit) {
		this.kit = kit;
	}

	/**
	 * Gets the kit.
	 *
	 * @return the kit
	 */
	public Kit getKit() {
		return kit;
	}

	@Override
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
