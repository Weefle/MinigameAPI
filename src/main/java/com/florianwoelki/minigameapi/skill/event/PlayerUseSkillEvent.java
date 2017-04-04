package com.florianwoelki.minigameapi.skill.event;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

import com.florianwoelki.minigameapi.skill.Skill;

public class PlayerUseSkillEvent extends PlayerEvent {

	private static final HandlerList handlers = new HandlerList();

	private Skill skill;
	private boolean isSuccess;

	public PlayerUseSkillEvent(Player player, Skill skill, boolean isSuccess) {
		super(player);
		this.skill = skill;
		this.isSuccess = isSuccess;
	}

	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	public boolean isSuccess() {
		return isSuccess;
	}

	public void setSkill(Skill skill) {
		this.skill = skill;
	}

	public Skill getSkill() {
		return skill;
	}

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}

}
