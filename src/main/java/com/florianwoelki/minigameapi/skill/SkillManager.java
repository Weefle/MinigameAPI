package com.florianwoelki.minigameapi.skill;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;

import com.florianwoelki.minigameapi.Manager;
import com.florianwoelki.minigameapi.MinigameAPI;
import com.florianwoelki.minigameapi.skill.listener.SkillListener;

public class SkillManager extends Manager {

	private final List<Skill> skills = new ArrayList<>();
	private final Map<UUID, Long> cooldown = new HashMap<>();

	public SkillManager(Skill... skills) {
		for(Skill skill : skills) {
			this.skills.add(skill);
		}
	}

	@Override
	public void onLoad() {
		Bukkit.getPluginManager().registerEvents(new SkillListener(this), MinigameAPI.getInstance());
	}

	@Override
	public void onUnload() {
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
