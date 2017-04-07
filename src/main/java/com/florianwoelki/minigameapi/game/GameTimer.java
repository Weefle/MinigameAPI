package com.florianwoelki.minigameapi.game;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import com.florianwoelki.minigameapi.Manager;
import com.florianwoelki.minigameapi.MinigameAPI;
import com.florianwoelki.minigameapi.config.ConfigData;
import com.florianwoelki.minigameapi.messenger.MessageType;
import com.florianwoelki.minigameapi.messenger.Messenger;

/**
 * The Class GameTimer.
 */
public class GameTimer implements Runnable {

	/** The is broadcasting messages. */
	private boolean isBroadcastingMessages = true;

	/** The time. */
	private int time = -1;

	/** The is finished. */
	private boolean isFinished;

	@Override
	public void run() {
		if(time == -1) {
			return;
		}

		time -= 1;
		for(Manager manager : MinigameAPI.getInstance().getManagers()) {
			manager.onUpdateTimer(time);
		}

		if(MinigameAPI.getInstance().getGame().getGameState().equals(GameState.LOBBY)) {
			for(Player onlinePlayer : Bukkit.getOnlinePlayers()) {
				onlinePlayer.setLevel(time);
			}
		}

		if(time == 0) {
			time = -1;
			finishCountdown();
		} else {
			broadcastState(time);
		}
	}

	/**
	 * Reset time.
	 */
	public void resetTime() {
		switch(MinigameAPI.getInstance().getGame().getGameState()) {
		case INGAME:
			time = ConfigData.gameDuration;
			break;
		case LOBBY:
			time = ConfigData.lobbyCountdown;
			break;
		case NONE:
			time = 15;
			break;
		case LOBBY_WITH_NOY_PLAYERS:
			time = ConfigData.lobbyCountdown;
			break;
		default:
			time = -1;
		}

		isBroadcastingMessages = true;
	}

	/**
	 * Broadcast state.
	 *
	 * @param time
	 *            the time
	 */
	public void broadcastState(int time) {
		if(!isBroadcastingMessages) {
			return;
		}

		switch(MinigameAPI.getInstance().getGame().getGameState()) {
		case INGAME:
			break;
		case LOBBY:
			if(time % 60 == 0 && time != 60) {
				Messenger.getInstance().broadcast(MessageType.INFO, "The game will start in §3" + (time / 60) + " §7minutes.");
				playSound(Sound.ORB_PICKUP);
			} else if(time == 60 || time == 30 || time == 10 || time == 5 || time == 4 || time == 3 || time == 2 || time == 1) {
				Messenger.getInstance().broadcast(MessageType.INFO, "The game will start in §3" + time + " §7seconds.");
				playSound(Sound.ORB_PICKUP);
			}
			break;
		case NONE:
			if(time == 14) {
				time = 15;
			}

			if(time == 15 || time <= 3) {
				Messenger.getInstance().broadcast(MessageType.BAD, "The server will stop in §3" + time + " §7seconds.");
				playSound(Sound.LAVA_POP);
			}
			break;
		default:
			break;
		}
	}

	/**
	 * Finish countdown.
	 */
	public void finishCountdown() {
		switch(MinigameAPI.getInstance().getGame().getGameState()) {
		case INGAME:
			break;
		case LOBBY:
			MinigameAPI.getInstance().getGame().startGame();
			break;
		case NONE:
			MinigameAPI.getInstance().getGame().doShutdown();
			break;
		case LOBBY_WITH_NOY_PLAYERS:
			if(Bukkit.getOnlinePlayers().size() != 0) {
				int diff = ConfigData.minimumPlayers - Bukkit.getOnlinePlayers().size();

				if(diff == 1) {
					Messenger.getInstance().broadcast(MessageType.INFO, "If §3one §7player join, the game will start.");
				} else {
					Messenger.getInstance().broadcast(MessageType.INFO, "If §3" + diff + " §7players join, the game will start.");
				}
			}
			resetTime();
		}
	}

	/**
	 * Play sound.
	 *
	 * @param sound
	 *            the sound
	 */
	private void playSound(Sound sound) {
		for(Player onlinePlayer : Bukkit.getOnlinePlayers()) {
			onlinePlayer.playSound(onlinePlayer.getLocation(), sound, 1f, 1f);
		}
	}

	/**
	 * Sets the broadcasting messages.
	 *
	 * @param isBroadcastingMessages
	 *            the new broadcasting messages
	 */
	public void setBroadcastingMessages(boolean isBroadcastingMessages) {
		this.isBroadcastingMessages = isBroadcastingMessages;
	}

	/**
	 * Checks if is broadcasting messages.
	 *
	 * @return true, if is broadcasting messages
	 */
	public boolean isBroadcastingMessages() {
		return isBroadcastingMessages;
	}

	/**
	 * Sets the time.
	 *
	 * @param time
	 *            the new time
	 */
	public void setTime(int time) {
		this.time = time;
	}

	/**
	 * Gets the time.
	 *
	 * @return the time
	 */
	public int getTime() {
		return time;
	}

}
