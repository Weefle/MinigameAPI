package com.florianwoelki.minigameapi.tracker.listener;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.bukkit.entity.EntityType;
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
import org.bukkit.inventory.ItemStack;

import com.florianwoelki.minigameapi.MinigameAPI;
import com.florianwoelki.minigameapi.messenger.MessageType;
import com.florianwoelki.minigameapi.messenger.Messenger;
import com.florianwoelki.minigameapi.tracker.TrackerItem;
import com.florianwoelki.minigameapi.tracker.TrackerRemover;

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
		if(event.getEntity().getType() != EntityType.PLAYER) {
			return;
		}

		Player player = event.getEntity();

		Iterator<Map.Entry<Player, Player>> itr = trackerItem.getPlayerTracker().entrySet().iterator();
		while(itr.hasNext()) {
			Map.Entry<Player, Player> e = itr.next();
			if(e.getKey() == player) {
				itr.remove();
			} else if(e.getValue() == player) {
				trackerItem.retrack(e.getKey());
			}
		}

		Iterator<Map.Entry<Player, List<Player>>> itr2 = TrackerRemover.getInstance().getPlayers().entrySet().iterator();
		while(itr2.hasNext()) {
			Map.Entry<Player, List<Player>> e = itr2.next();
			if(e.getKey() == player) {
				itr2.remove();
			} else if(e.getValue().contains(player)) {
				e.getValue().remove(player);
			}
		}

		trackerItem.getTrackers().remove(player);
	}

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		trackerItem.getTrackers().remove(player);

		Iterator<Map.Entry<Player, Player>> itr = trackerItem.getPlayerTracker().entrySet().iterator();
		while(itr.hasNext()) {
			Map.Entry<Player, Player> e = itr.next();
			if((e.getKey() == player) || (e.getValue() == player)) {
				itr.remove();
			}
		}
		
		Iterator<Map.Entry<Player, List<Player>>> itr2 = TrackerRemover.getInstance().getPlayers().entrySet().iterator();
		while(itr2.hasNext()) {
			Map.Entry<Player, List<Player>> e = itr2.next();
			if(e.getKey() == player) {
				itr.remove();
			} else if(e.getValue().contains(player)) {
				e.getValue().remove(player);
			}
		}
	}

	@EventHandler
	public void onPlayerHeldItem(PlayerItemHeldEvent event) {
		Player player = event.getPlayer();
		if(MinigameAPI.getInstance().getSpectators().contains(player) || !MinigameAPI.getInstance().getGame().isGameStarted()) {
			return;
		}

		ItemStack newSlot = player.getInventory().getItem(event.getNewSlot());
		if(trackerItem.isTrackerItem(newSlot)) {
			if(!trackerItem.getTrackers().contains(player)) {
				trackerItem.getTrackers().add(player);
			}
			if(!trackerItem.getPlayerTracker().containsKey(player)) {
				trackerItem.retrack(player);
			}
		} else if(trackerItem.getTrackers().contains(player)) {
			trackerItem.getTrackers().remove(player);
		}
	}

	@EventHandler
	public void onInteract(PlayerInteractEntityEvent event) {
		if(!(event.getRightClicked() instanceof Player)) {
			return;
		}

		Player player = event.getPlayer();
		Player clicked = (Player) event.getRightClicked();
		if(!trackerItem.isTrackerItem(player.getItemInHand())) {
			return;
		}

		if(MinigameAPI.getInstance().getSpectators().contains(player) || MinigameAPI.getInstance().getSpectators().contains(clicked)) {
			return;
		}

		if(trackerItem.getMaxPlayersToRemove() == -1) {
			Messenger.getInstance().message(player, MessageType.BAD, "In this round you can't remove players from your tracker.");
			return;
		}

		if(TrackerRemover.getInstance().hasIgnored(player, clicked)) {
			TrackerRemover.getInstance().unignorePlayer(trackerItem, player, clicked);
			Messenger.getInstance().message(player, MessageType.GOOD, "The player §a" + clicked.getDisplayName() + " §6is going to be tracked again.");
			return;
		}

		if(TrackerRemover.getInstance().getIgnoredPlayers(player).size() >= trackerItem.getMaxPlayersToRemove()) {
			Messenger.getInstance().message(player, MessageType.BAD, "You can't remove more players from the tracker.");
			return;
		}

		TrackerRemover.getInstance().ignorePlayer(trackerItem, player, clicked);
		Messenger.getInstance().message(player, MessageType.GOOD, "You are now not tracking the player §a" + clicked.getDisplayName() + " §6anymore.");
	}

}
