package com.florianwoelki.minigameapi.tracker.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.florianwoelki.minigameapi.tracker.TrackerItem;

public class TrackerListener implements Listener {

	private TrackerItem trackerItem;
	
	public TrackerListener(TrackerItem trackerItem) {
		this.trackerItem = trackerItem;
	}
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		Player player = event.getPlayer();

		if(event.getAction() != Action.LEFT_CLICK_AIR && event.getAction() != Action.LEFT_CLICK_BLOCK) {
			return;
		}
		
		if(trackerItem.isTrackerItem(event.getItem())) {
			if(trackerItem.getTrackers().contains(player)) {
				trackerItem.getTrackers().add(player);
			}
			
			trackerItem.retrack(player);
		}
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void onPlayerDeath(PlayerDeathEvent event) {
		
	}

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {

	}

	@EventHandler
	public void onPlayerHeldItem(PlayerItemHeldEvent event) {

	}

	@EventHandler
	public void onInteract(PlayerInteractEntityEvent event) {

	}

}
