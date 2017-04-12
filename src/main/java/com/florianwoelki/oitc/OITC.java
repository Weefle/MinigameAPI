package com.florianwoelki.oitc;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.florianwoelki.minigameapi.MinigameAPI;
import com.florianwoelki.minigameapi.api.Minigame;
import com.florianwoelki.minigameapi.api.StopReason;
import com.florianwoelki.minigameapi.api.util.Title;
import com.florianwoelki.minigameapi.location.LocationManager;
import com.florianwoelki.minigameapi.messenger.Messenger;
import com.florianwoelki.minigameapi.spectator.SpectatorManager;
import com.florianwoelki.oitc.command.CommandSetSpawn;
import com.florianwoelki.oitc.listener.OITCListener;
import com.florianwoelki.oitc.timer.ProtectionTimer;
import com.florianwoelki.oitc.util.ScoreboardManager;

public class OITC extends JavaPlugin implements Minigame {

	private static OITC instance;

	private Map<Player, Integer> playerKills = new HashMap<>();
	private Map<Player, Integer> deaths = new HashMap<>();

	@Override
	public void onEnable() {
		instance = this;

		MinigameAPI.getInstance().initializeMinigame(this);
		MinigameAPI.getInstance().setChatPrefix("OITC");
		MinigameAPI.getInstance().setMinigameName("OITC");
		MinigameAPI.getInstance().setAllowSpectatorDeath(false);
		MinigameAPI.getInstance().setBlockChangesEnabled(false);

		MinigameAPI.getInstance().enableDatabase();
		MinigameAPI.getInstance().enableAchievements();

		MinigameAPI.getInstance().getCommandHandler().register(CommandSetSpawn.class, new CommandSetSpawn());
		Bukkit.getPluginManager().registerEvents(new OITCListener(), this);
	}

	@Override
	public void onDisable() {
		instance = null;
	}

	@Override
	public void startGame() {
		int counter = 0;
		for(Player onlinePlayer : Bukkit.getOnlinePlayers()) {
			SpectatorManager.leaveSpectator(onlinePlayer);

			onlinePlayer.getInventory().clear();
			onlinePlayer.setLevel(0);

			counter++;
			onlinePlayer.teleport(LocationManager.getInstance().getLocation("Spawn." + counter));
			Bukkit.getWorld(onlinePlayer.getWorld().getName()).setAutoSave(false);

			onlinePlayer.playSound(onlinePlayer.getLocation(), Sound.LEVEL_UP, 1f, 0f);

			playerKills.put(onlinePlayer, 0);
			ScoreboardManager.updateScoreboard(onlinePlayer);
		}

		for(Entity entity : MinigameAPI.getInstance().getIngamePlayers().get(0).getWorld().getEntities()) {
			if(!(entity instanceof Player)) {
				entity.remove();
			}
		}

		ProtectionTimer protectionTimer = new ProtectionTimer();
		protectionTimer.start();
	}

	@Override
	public void stopGame(StopReason stopReason) {
		Map.Entry<Player, Integer> maxEntry = null;
		for(Map.Entry<Player, Integer> entry : playerKills.entrySet()) {
			if(maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) > 0) {
				maxEntry = entry;
			}
		}

		final Player player = maxEntry.getKey();

		for(Entity entity : maxEntry.getKey().getWorld().getEntities()) {
			if(!(entity instanceof Player)) {
				entity.remove();
			}
		}

		Bukkit.getScheduler().cancelAllTasks();

		Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
			@Override
			public void run() {
				MinigameAPI.getInstance().getGame().doShutdown();
			}
		}, 10L * 20L);

		Messenger.getInstance().spaceBroadcast(" ");
		Messenger.getInstance().spaceBroadcast("§3Game Over... §8| §cThe server will restart in 10 seconds.", "§6Winner§8: §c" + player.getName());
		Messenger.getInstance().spaceBroadcast(" ");

		Title title = new Title("§6Winner:", "§5§o" + player.getName(), 1, 2, 1);
		title.send();

		MinigameAPI.getInstance().getGame().stopGame(StopReason.GAME_END);
	}

	public Map<Player, Integer> getPlayerKills() {
		return playerKills;
	}

	public Map<Player, Integer> getDeaths() {
		return deaths;
	}

	public static OITC getInstance() {
		return instance;
	}
	
}
