package com.florianwoelki.minigameapi.skill.event;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

import com.florianwoelki.minigameapi.skill.Skill;

public class PlayerGiveSkillEvent extends PlayerEvent {

	private static final HandlerList handlers = new HandlerList();

	private Skill skill;

	public PlayerGiveSkillEvent(Player player, Skill skill) {
		super(player);
		this.skill = skill;
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
