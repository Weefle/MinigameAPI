package com.florianwoelki.minigameapi.skill.event;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

import com.florianwoelki.minigameapi.skill.Skill;

/**
 * This event gets called when the player uses a specific skill.
 */
public class PlayerUseSkillEvent extends PlayerEvent {

	/** The Constant handlers. */
	private static final HandlerList handlers = new HandlerList();

	/** The skill. */
	private Skill skill;

	/** The is success. */
	private boolean isSuccess;

	/**
	 * Instantiates a new player use skill event.
	 *
	 * @param player
	 *            the player
	 * @param skill
	 *            the skill
	 * @param isSuccess
	 *            the is success
	 */
	public PlayerUseSkillEvent(Player player, Skill skill, boolean isSuccess) {
		super(player);
		this.skill = skill;
		this.isSuccess = isSuccess;
	}

	/**
	 * Sets the success.
	 *
	 * @param isSuccess
	 *            the new success
	 */
	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	/**
	 * Checks if is success.
	 *
	 * @return true, if is success
	 */
	public boolean isSuccess() {
		return isSuccess;
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
