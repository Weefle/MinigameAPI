package com.florianwoelki.minigameapi.kit.event;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

import com.florianwoelki.minigameapi.kit.Kit;

public class PlayerGiveKitEvent extends PlayerEvent {

	private static HandlerList handlers = new HandlerList();

	private Kit kit;

	public PlayerGiveKitEvent(Player player, Kit kit) {
		super(player);
		this.kit = kit;
	}

	public void setKit(Kit kit) {
		this.kit = kit;
	}

	public Kit getKit() {
		return kit;
	}

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}

}
