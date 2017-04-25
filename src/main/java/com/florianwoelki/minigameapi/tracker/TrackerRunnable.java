package com.florianwoelki.minigameapi.tracker;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.florianwoelki.minigameapi.MinigameAPI;
import com.florianwoelki.minigameapi.api.util.ActionBar;

/**
 * The Class TrackerRunnable.
 */
public class TrackerRunnable implements Runnable {

	/** The task id. */
	private int taskId = -1;
	
	/** The tracker item. */
	private TrackerItem trackerItem;
	
	/** The action bar. */
	private ActionBar actionBar;
	
	/**
	 * Instantiates a new tracker runnable.
	 *
	 * @param trackerItem the tracker item
	 */
	public TrackerRunnable(TrackerItem trackerItem) {
		this.trackerItem = trackerItem;
		this.actionBar = new ActionBar();
	}

	/**
	 * Start.
	 */
	public void start() {
		taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(MinigameAPI.getInstance(), this, 0l, 5l);
	}
	
	/**
	 * Cancel.
	 */
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
	
	/**
	 * Gets the distance.
	 *
	 * @param player the player
	 * @return the distance
	 */
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
	
	/**
	 * Gets the task id.
	 *
	 * @return the task id
	 */
	public int getTaskId() {
		return taskId;
	}

}
