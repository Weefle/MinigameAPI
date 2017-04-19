package com.florianwoelki.minigameapi.tracker;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.florianwoelki.minigameapi.MinigameAPI;
import com.florianwoelki.minigameapi.api.util.ActionBar;

public class TrackerRunnable implements Runnable {

	private int taskId = -1;
	
	private TrackerItem trackerItem;
	private ActionBar actionBar;
	
	public TrackerRunnable(TrackerItem trackerItem) {
		this.trackerItem = trackerItem;
		this.actionBar = new ActionBar();
	}

	public void start() {
		taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(MinigameAPI.getInstance(), this, 0l, 5l);
	}
	
	public void cancel() {
		Bukkit.getScheduler().cancelTask(taskId);
		taskId = -1;
	}
	
	@Override
	public void run() {
		if(trackerItem.getTrackers().isEmpty()) {
			return;
		}
		
		for(Player player : trackerItem.getTrackers()) {
			if(trackerItem.isTrackerItem(player.getItemInHand())) {
				Player target = trackerItem.getPlayerTracker().get(player);
				if(target != null) {
					actionBar.setMessage("§6Tracker §8» §fPlayer: §6" + target.getDisplayName() + "  §fDistance: §6" + getDistance(player));
					player.setCompassTarget(target.getLocation());
				} else {
					actionBar.setMessage("§6Tracker §8» §cNo player near you!");
					actionBar.send(player);
					player.setCompassTarget(player.getLocation());
					trackerItem.retrack(player);
				}
			}
		}
	}
	
	public int getDistance(Player player) {
		Player target = trackerItem.getPlayerTracker().get(player);
		if(MinigameAPI.getInstance().getSpectators().contains(target)) {
			trackerItem.retrack(player);
		}
		
		if(player.getWorld() != target.getWorld()) {
			return -1;
		}
		
		return (int) Math.round(player.getLocation().distance(target.getLocation()));
	}
	
	public int getTaskId() {
		return taskId;
	}

}
