package com.florianwoelki.minigameapi.skill.event;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

import com.florianwoelki.minigameapi.skill.Skill;

/**
 * This event gets called when the player will receive a specific skill.
 */
public class PlayerGiveSkillEvent extends PlayerEvent {

	/** The Constant handlers. */
	private static final HandlerList handlers = new HandlerList();

	/** The skill. */
	private Skill skill;

	/**
	 * Instantiates a new player give skill event.
	 *
	 * @param player
	 *            the player
	 * @param skill
	 *            the skill
	 */
	public PlayerGiveSkillEvent(Player player, Skill skill) {
		super(player);
		this.skill = skill;
	}

	/**
	 * Sets the skill.
	 *
	 * @param skill
	 *            the new skill
	 */
	public void setSkill(Skill skill) {
		this.skill = skill;
	}

	/**
	 * Gets the skill.
	 *
	 * @return the skill
	 */
	public Skill getSkill() {
		return skill;
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
