package com.florianwoelki.minigameapi.util;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.florianwoelki.minigameapi.MinigameAPI;
import com.florianwoelki.minigameapi.api.util.ActionBar;
import com.florianwoelki.minigameapi.game.GameState;

/**
 * The Class ActionBarBroadcaster.
 */
public class ActionBarBroadcaster {

	/** The messages. */
	private String[] messages = { //
			"§bYou are playing §e§l" + MinigameAPI.getInstance().getMinigameName(), //
			"§bNew §6§lUpdates!" //
	};

	/** The index. */
	private int index = 0;

	/** The task. */
	private int task;

	/** The player. */
	private Player player;

	/** The action bar. */
	private ActionBar actionBar;

	/**
	 * Instantiates a new action bar broadcaster.
	 *
	 * @param player
	 *            the player
	 */
	public ActionBarBroadcaster(Player player) {
		this.player = player;
		this.actionBar = new ActionBar();
	}

	/**
	 * Start broadcast.
	 */
	@SuppressWarnings("deprecation")
	public void startBroadcast() {
		task = Bukkit.getScheduler().scheduleAsyncRepeatingTask(MinigameAPI.getInstance(), new Runnable() {
			@Override
			public void run() {
				if(MinigameAPI.getInstance().getGame().getGameState().equals(GameState.INGAME)) {
					Bukkit.getScheduler().cancelTask(task);
					return;
				}

				index++;
				if(index >= messages.length) {
					index = 0;
				}

				actionBar.setMessage(messages[index]);
				actionBar.send(player);
			}
		}, 0L, 2 * 20L);
	}

}
