package com.florianwoelki.minigameapi.skill;

import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

/**
 * The Interface SkillAction.
 */
public interface SkillAction {

	/**
	 * Execute.
	 *
	 * @param event
	 *            the event
	 */
	void execute(PlayerInteractEvent event);

	/**
	 * Gets the item stack.
	 *
	 * @return the item stack
	 */
	ItemStack getItemStack();

}
