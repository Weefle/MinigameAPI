package com.florianwoelki.minigameapi;

import org.bukkit.entity.Player;

import com.florianwoelki.minigameapi.api.StopReason;

/**
 * This class represents a abstract Manager class
 * This class is for every manager class
 */
public abstract class Manager {

	/**
	 * This method will be called when the manager will be enabled
	 */
	public abstract void onLoad();

	/**
	 * This method will be called when the manager will be disabled
	 */
	public abstract void onUnload();

	/**
	 * This method will be called when the lobby phase will start
	 */
	public void onStartLobbyPhase() {
	}

	/**
	 * This method will be called when the game starts
	 */
	public void onStartGame() {
	}

	/**
	 * This method will be called when the game stops
	 */
	public void onStopGame(StopReason stopReason) {
	}

	/**
	 * This method will be called when a player will join
	 */
	public void onPlayerJoin(Player player) {
	}

	/**
	 * This method will be called when a player will quit
	 */
	public void onPlayerQuit(Player player) {
	}

	/**
	 * This method will be called when the timer will be updated
	 */
	public void onUpdateTimer(int newTime) {
	}

}
