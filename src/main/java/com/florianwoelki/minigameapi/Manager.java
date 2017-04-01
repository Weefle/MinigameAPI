package com.florianwoelki.minigameapi;

import org.bukkit.entity.Player;

import com.florianwoelki.minigameapi.api.StopReason;

public abstract class Manager {

	public abstract void onLoad();

	public abstract void onUnload();

	public void onStartLobbyPhase() {
	}

	public void onStartGame() {
	}

	public void onStopGame(StopReason stopReason) {
	}

	public void onPlayerJoin(Player player) {
	}

	public void onPlayerQuit(Player player) {
	}

	public void onUpdateTimer(int newTime) {
	}

}
