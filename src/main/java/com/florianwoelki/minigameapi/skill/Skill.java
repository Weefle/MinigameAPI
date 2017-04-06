package com.florianwoelki.minigameapi.skill;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.metadata.FixedMetadataValue;

import com.florianwoelki.minigameapi.MinigameAPI;
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
		player.getInventory().addItem(getItemStack());

		ItemStack item = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
		SkullMeta skullMeta = (SkullMeta) item.getItemMeta();

		skullMeta.setOwner(player.getName());
		item.setItemMeta(skullMeta);

		player.setMetadata("skill", new FixedMetadataValue(MinigameAPI.getInstance(), name));
		Bukkit.getPluginManager().callEvent(new PlayerGiveSkillEvent(player, this));
	}

	public String getName() {
		return name;
	}

	public int getCooldown() {
		return cooldown;
	}

}
