package com.florianwoelki.minigameapi.game;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
import org.bukkit.entity.Player;

import com.florianwoelki.minigameapi.Manager;
import com.florianwoelki.minigameapi.MinigameAPI;
import com.florianwoelki.minigameapi.api.StopReason;
import com.florianwoelki.minigameapi.config.ConfigData;
import com.florianwoelki.minigameapi.messenger.MessageType;
import com.florianwoelki.minigameapi.messenger.Messenger;

public class Game {

	private GameState gameState;

	public Game() {
		this.gameState = GameState.LOBBY_WITH_NOY_PLAYERS;
	}

	public void startLobbyPhase() {
		if(gameState != GameState.LOBBY_WITH_NOY_PLAYERS) {
			return;
		}

		gameState = GameState.LOBBY;
		for(Player onlinePlayer : Bukkit.getOnlinePlayers()) {
			MinigameAPI.getInstance().giveLobbyItems(onlinePlayer);
		}

		for(Manager manager : MinigameAPI.getInstance().getManagers()) {
			manager.onStartLobbyPhase();
		}

		Bukkit.broadcastMessage(" ");
		Messenger.getInstance().spaceBroadcast("§eThe countdown has been started!");
		Bukkit.broadcastMessage(" ");
	}

	public void startGame() {
		startGame(false);
	}

	public void startGame(boolean isForceStart) {
		if(gameState == GameState.INGAME) {
			return;
		}

		if(!isForceStart && Bukkit.getOnlinePlayers().size() < ConfigData.minimumPlayers) {
			MinigameAPI.getInstance().getGameTimer().resetTime();
			int diff = ConfigData.minimumPlayers - Bukkit.getOnlinePlayers().size();
			Messenger.getInstance().broadcast(MessageType.INFO, "If §3" + diff + " §7players will join, the round will start.");
			gameState = GameState.LOBBY_WITH_NOY_PLAYERS;
			return;
		}

		for(Manager manager : MinigameAPI.getInstance().getManagers()) {
			manager.onStartGame();
		}

		gameState = GameState.INGAME;
		MinigameAPI.getInstance().getMinigame().startGame();
	}

	public void stopGame(StopReason stopReason) {
		if(gameState != GameState.INGAME) {
			return;
		}

		gameState = GameState.NONE;

		if(MinigameAPI.getInstance().getMinigame() != null) {
			MinigameAPI.getInstance().getMinigame().stopGame(stopReason);
		}
	}

	public void doShutdown() {
		for(Player onlinePlayer : Bukkit.getOnlinePlayers()) {
			onlinePlayer.kickPlayer("§cServer is restarting...");
		}

		Bukkit.getScheduler().runTaskLater(MinigameAPI.getInstance(), new Runnable() {
			@Override
			public void run() {
				Bukkit.getServer().shutdown();
			}
		}, 100l);
	}

	public void setGameState(GameState gameState) {
		this.gameState = gameState;
		if(gameState.equals(GameState.LOBBY) || gameState.equals(GameState.LOBBY_WITH_NOY_PLAYERS)) {
			((CraftServer) Bukkit.getServer()).getServer().setMotd("lobby");
		} else if(gameState.equals(GameState.INGAME)) {
			((CraftServer) Bukkit.getServer()).getServer().setMotd("ingame");
		} else if(gameState.equals(GameState.NONE)) {
			((CraftServer) Bukkit.getServer()).getServer().setMotd("restart");
		}
	}

	public boolean isGameStarted() {
		return gameState == GameState.INGAME;
	}

	public GameState getGameState() {
		return gameState;
	}

}
