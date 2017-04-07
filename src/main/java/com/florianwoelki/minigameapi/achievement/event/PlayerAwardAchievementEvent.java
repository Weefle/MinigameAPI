package com.florianwoelki.minigameapi.achievement.event;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

import com.florianwoelki.minigameapi.achievement.Achievement;

/**
 * The event PlayerAwardAchievementEvent gets called when a player award a achievement.
 */
public class PlayerAwardAchievementEvent extends PlayerEvent {

	private static final HandlerList HANDLERS = new HandlerList();

	private Achievement achievement;

	/**
	 * Instantiates a new player award achievement event.
	 *
	 * @param player
	 *            the player who award the achievement
	 * @param achievement
	 *            the achievement for the player
	 */
	public PlayerAwardAchievementEvent(Player player, Achievement achievement) {
		super(player);
		this.achievement = achievement;
	}

	/**
	 * Gets the achievement.
	 *
	 * @return the achievement
	 */
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
