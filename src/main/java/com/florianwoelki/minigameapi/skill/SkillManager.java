package com.florianwoelki.minigameapi.skill;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;

import com.florianwoelki.minigameapi.Manager;
import com.florianwoelki.minigameapi.MinigameAPI;
import com.florianwoelki.minigameapi.skill.listener.SkillListener;

public class SkillManager extends Manager {

	private final List<Skill> skills = new ArrayList<>();
	private final Map<UUID, Long> cooldown = new HashMap<>();

	private SkillListener skillListener;

	public SkillManager(Skill... skills) {
		this.skillListener = new SkillListener(this);
		for(Skill skill : skills) {
			this.skills.add(skill);
		}
	}

	@Override
	public void onLoad() {
		Bukkit.getPluginManager().registerEvents(skillListener, MinigameAPI.getInstance());
	}

	@Override
	public void onUnload() {
		HandlerList.unregisterAll(skillListener);
	}

	public void addSkill(Skill skill) {
		skills.add(skill);
	}

	public List<Skill> getSkills() {
		return skills;
	}

	public Map<UUID, Long> getCooldown() {
		return cooldown;
	}

}
