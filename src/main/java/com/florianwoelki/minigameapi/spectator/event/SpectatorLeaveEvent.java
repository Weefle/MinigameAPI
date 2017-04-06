package com.florianwoelki.minigameapi.spectator.event;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

public class SpectatorLeaveEvent extends PlayerEvent {

	private static final HandlerList handlers = new HandlerList();

	public SpectatorLeaveEvent(Player player) {
		super(player);
	}

	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}

}
