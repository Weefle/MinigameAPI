package com.florianwoelki.minigameapi.skill;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.florianwoelki.minigameapi.player.PlayerData;
import com.florianwoelki.minigameapi.player.PlayerWrapper;
import com.florianwoelki.minigameapi.skill.event.PlayerGiveSkillEvent;

public abstract class Skill implements SkillAction {

	protected String name;
	protected int cooldown;

	public Skill(String name) {
		this.name = name;
	}

	public Skill(String name, int cooldown) {
		this.name = name;
		this.cooldown = cooldown;
	}

	public void giveSkill(Player player) {
		PlayerWrapper playerWrapper = (PlayerWrapper) player;

		playerWrapper.getInventory().addItem(getItemStack());
		playerWrapper.addMetadata("skill", new PlayerData<Skill>(this));
		Bukkit.getPluginManager().callEvent(new PlayerGiveSkillEvent(player, this));
	}

	public String getName() {
		return name;
	}

	public int getCooldown() {
		return cooldown;
	}

}
