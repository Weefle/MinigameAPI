package com.florianwoelki.minigameapi;

import org.bukkit.entity.Player;

import com.florianwoelki.minigameapi.api.StopReason;

public abstract class Manager {

	public abstract void load();

	public abstract void unload();

	public void startLobbyPhase() {
	}

	public void startGame() {
	}

	public void stopGame(StopReason stopReason) {
	}

	public void joinPlayer(Player player) {
	}

	public void leaveTimer(Player player) {
	}

	public void updateTimer(int newTime) {
	}

}
