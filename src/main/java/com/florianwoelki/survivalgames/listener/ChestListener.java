package com.florianwoelki.survivalgames.listener;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;

import com.florianwoelki.minigameapi.MinigameAPI;
import com.florianwoelki.survivalgames.SurvivalGames;

public class ChestListener implements Listener {

	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	public void onPlayerInteract(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		Block block = event.getClickedBlock();
		if(event.getAction() == Action.RIGHT_CLICK_BLOCK && MinigameAPI.getInstance().getGame().isGameStarted()) {
			if(MinigameAPI.getInstance().getSpectators().contains(player)) {
				return;
			}

			if(block.getType() == Material.PRISMARINE) {
				String locationString = "";
				if(SurvivalGames.getInstance().getUsedChests().containsKey(locationString)) {
					Inventory inventory = SurvivalGames.getInstance().getUsedChests().get(locationString);
					player.openInventory(inventory);
				} else {
					Inventory inventory = Bukkit.createInventory(null, 27);
					SurvivalGames.getInstance().getUsedChests().put(locationString, inventory);
					player.openInventory(inventory);
				}
			}
		}
	}

}
