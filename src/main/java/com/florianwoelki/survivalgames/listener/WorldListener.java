package com.florianwoelki.survivalgames.listener;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.BlockSpreadEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;
import org.bukkit.event.hanging.HangingPlaceEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;

import com.florianwoelki.minigameapi.MinigameAPI;

public class WorldListener implements Listener {

	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		if(MinigameAPI.getInstance().getGame().isGameStarted()) {
			switch(event.getBlock().getType()) {
			case LEAVES:
			case LEAVES_2:
			case WEB:
			case CAKE:
			case CAKE_BLOCK:
			case VINE:
			case SEEDS:
			case WHEAT:
			case LONG_GRASS:
			case MELON_BLOCK:
			case CARROT:
			case POTATO:
				return;
			default:
				break;
			}

			event.setCancelled(true);
		}
	}

	@EventHandler
	public void onBlockPlace(BlockPlaceEvent event) {
		Player player = event.getPlayer();
		ItemStack itemStack = event.getItemInHand();
		if(itemStack.getType() == Material.TNT) {
			if(itemStack.getAmount() == 1) {
				player.setItemInHand(null);
			} else {
				itemStack.setAmount(itemStack.getAmount() - 1);
				player.setItemInHand(itemStack);
			}

			event.setCancelled(true);
			event.getBlock().getWorld().spawnEntity(event.getBlock().getLocation(), EntityType.PRIMED_TNT);
			return;
		}

		switch(event.getBlock().getType()) {
		case WEB:
		case CAKE:
		case CAKE_BLOCK:
		case FLINT_AND_STEEL:
		case FIRE:
			return;
		default:
			break;
		}

		event.setCancelled(true);
	}

	@EventHandler
	public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
		if(event.getRightClicked().getType() != EntityType.ITEM_FRAME) {
			return;
		}

		event.setCancelled(true);
	}

	@EventHandler
	public void onHangingBreak(HangingBreakByEntityEvent event) {
		event.setCancelled(true);
	}

	@EventHandler
	public void onHangingPlace(HangingPlaceEvent event) {
		event.setCancelled(true);
	}

	@EventHandler
	public void onCreatureSpawn(CreatureSpawnEvent e) {
		if(e.getEntityType() == EntityType.HORSE || e.getEntityType() == EntityType.ZOMBIE || e.getEntityType() == EntityType.SQUID) {
			return;
		}

		e.setCancelled(true);
	}

	@EventHandler
	public void onDeath(EntityDeathEvent e) {
		if(e.getEntityType() == EntityType.SQUID) {
			e.getDrops().clear();
		}
	}

	@EventHandler
	public void onBurn(BlockBurnEvent e) {
		e.setCancelled(true);
	}

	@EventHandler
	public void onFireSpread(BlockSpreadEvent e) {
		e.setCancelled(true);
	}

}
