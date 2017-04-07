package com.florianwoelki.minigameapi.skill;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.metadata.FixedMetadataValue;

import com.florianwoelki.minigameapi.MinigameAPI;
import com.florianwoelki.minigameapi.skill.event.PlayerGiveSkillEvent;

/**
 * The Class Skill.
 */
public abstract class Skill implements SkillAction {

	/** The name. */
	protected String name;

	/** The cooldown. */
	protected int cooldown;

	/**
	 * Instantiates a new skill.
	 *
	 * @param name
	 *            the name
	 */
	public Skill(String name) {
		this.name = name;
	}

	/**
	 * Instantiates a new skill.
	 *
	 * @param name
	 *            the name
	 * @param cooldown
	 *            the cooldown
	 */
	public Skill(String name, int cooldown) {
		this.name = name;
		this.cooldown = cooldown;
	}

	/**
	 * Give skill.
	 *
	 * @param player
	 *            the player
	 */
	public void giveSkill(Player player) {
		player.getInventory().addItem(getItemStack());

		ItemStack item = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
		SkullMeta skullMeta = (SkullMeta) item.getItemMeta();

		skullMeta.setOwner(player.getName());
		item.setItemMeta(skullMeta);

		player.setMetadata("skill", new FixedMetadataValue(MinigameAPI.getInstance(), name));
		Bukkit.getPluginManager().callEvent(new PlayerGiveSkillEvent(player, this));
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Gets the cooldown.
	 *
	 * @return the cooldown
	 */
	public int getCooldown() {
		return cooldown;
	}

}
