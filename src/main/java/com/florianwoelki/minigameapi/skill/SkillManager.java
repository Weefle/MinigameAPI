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

/**
 * The Class SkillManager.
 */
public class SkillManager extends Manager {

	/** The skills. */
	private final List<Skill> skills = new ArrayList<>();

	/** The cooldown. */
	private final Map<UUID, Long> cooldown = new HashMap<>();

	/** The skill listener. */
	private SkillListener skillListener;

	/**
	 * Instantiates a new skill manager.
	 *
	 * @param skills
	 *            the skills
	 */
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

	/**
	 * Gets the skill by name.
	 *
	 * @param skillName
	 *            the skill name
	 * @return the skill by name
	 */
	public Skill getSkillByName(String skillName) {
		for(Skill skill : skills) {
			if(skill.getName().equalsIgnoreCase(skillName)) {
				return skill;
			}
		}

		return null;
	}

	/**
	 * Adds the skill.
	 *
	 * @param skill
	 *            the skill
	 */
	public void addSkill(Skill skill) {
		skills.add(skill);
	}

	/**
	 * Gets the skills.
	 *
	 * @return the skills
	 */
	public List<Skill> getSkills() {
		return skills;
	}

	/**
	 * Gets the cooldown.
	 *
	 * @return the cooldown
	 */
	public Map<UUID, Long> getCooldown() {
		return cooldown;
	}

}
