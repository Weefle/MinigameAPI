package com.florianwoelki.minigameapi.skill;

import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public interface SkillAction {

	void execute(PlayerInteractEvent event);

	ItemStack getItemStack();

}
