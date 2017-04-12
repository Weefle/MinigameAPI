package com.florianwoelki.oitc.timer;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.florianwoelki.minigameapi.MinigameAPI;
import com.florianwoelki.minigameapi.api.StopReason;
import com.florianwoelki.minigameapi.config.ConfigData;
import com.florianwoelki.oitc.OITC;

public class GameTimer implements Runnable {

	private int time = ConfigData.gameDuration;
	private int taskId = -1;

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
				onlinePlayer.setLevel(time);
			}
		} else {
			cancel();

			MinigameAPI.getInstance().getGame().stopGame(StopReason.GAME_END);

			for(Player onlinePlayer : Bukkit.getOnlinePlayers()) {
				onlinePlayer.setLevel(0);
			}
		}
	}

}
