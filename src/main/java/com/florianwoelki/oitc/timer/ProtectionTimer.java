package com.florianwoelki.oitc.timer;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import com.florianwoelki.minigameapi.api.util.Title;
import com.florianwoelki.minigameapi.messenger.MessageType;
import com.florianwoelki.minigameapi.messenger.Messenger;
import com.florianwoelki.oitc.OITC;
import com.florianwoelki.oitc.util.PlayerInventory;

public class ProtectionTimer implements Runnable {

	private int time = 15;
	private int taskId;

	public void start() {
		taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(OITC.getInstance(), this, 0l, 20l);
	}

	public void cancel() {
		Bukkit.getScheduler().cancelTask(taskId);
		taskId = -1;
	}

	@Override
	public void run() {
		if(time > 1) {
			time--;

			for(Player onlinePlayer : Bukkit.getOnlinePlayers()) {
				onlinePlayer.setLevel(0);
			}

			if(time == 10) {
				Title title = new Title("§6Prepare", "§cto fight!", 1, 2, 1);
				title.send();
			}

			if(time <= 5 || time == 10) {
				Messenger.getInstance().broadcast(MessageType.INFO, "The fight will start in §c" + time + " §7seconds.");
				for(Player onlinePlayer : Bukkit.getOnlinePlayers()) {
					onlinePlayer.playSound(onlinePlayer.getLocation(), Sound.ORB_PICKUP, 1f, 1f);
				}
			}
		} else {
			cancel();

			GameTimer gameTimer = new GameTimer();
			gameTimer.start();

			Messenger.getInstance().spaceBroadcast(" ");
			Messenger.getInstance().spaceBroadcast("§6Let's go! Have fun!");
			Messenger.getInstance().spaceBroadcast(" ");

			for(Player onlinePlayer : Bukkit.getOnlinePlayers()) {
				onlinePlayer.setLevel(0);
				PlayerInventory.givePlayerItems(onlinePlayer);
			}
		}
	}

}
