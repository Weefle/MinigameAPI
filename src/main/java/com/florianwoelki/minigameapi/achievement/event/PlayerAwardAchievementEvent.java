package com.florianwoelki.minigameapi.achievement.event;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

import com.florianwoelki.minigameapi.achievement.Achievement;

public class PlayerAwardAchievementEvent extends PlayerEvent {

	private static final HandlerList HANDLERS = new HandlerList();

	private Achievement achievement;

	public PlayerAwardAchievementEvent(Player player, Achievement achievement) {
		super(player);
		this.achievement = achievement;
	}

	public Achievement getAchievement() {
		return achievement;
	}

	@Override
	public HandlerList getHandlers() {
		return HANDLERS;
	}

	public static HandlerList getHandlerList() {
		return HANDLERS;
	}

}
