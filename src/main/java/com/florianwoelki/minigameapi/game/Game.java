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

/**
 * The Class Game.
 */
public class Game {

	/** The game state. */
	private GameState gameState;

	/**
	 * Instantiates a new game.
	 */
	public Game() {
		this.gameState = GameState.LOBBY_WITH_NOY_PLAYERS;
	}

	/**
	 * Start lobby phase.
	 */
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

	/**
	 * Start game.
	 */
	public void startGame() {
		startGame(false);
	}

	/**
	 * Start game.
	 *
	 * @param isForceStart
	 *            the is force start
	 */
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

		for(Player onlinePlayer : Bukkit.getOnlinePlayers()) {
			onlinePlayer.setLevel(0);
			onlinePlayer.setExp(0);
		}

		gameState = GameState.INGAME;
		MinigameAPI.getInstance().getMinigame().startGame();
	}

	/**
	 * Stop game.
	 *
	 * @param stopReason
	 *            the stop reason
	 */
	public void stopGame(StopReason stopReason) {
		if(gameState != GameState.INGAME) {
			return;
		}

		gameState = GameState.NONE;

		if(MinigameAPI.getInstance().getMinigame() != null) {
			MinigameAPI.getInstance().getMinigame().stopGame(stopReason);
		}
	}

	/**
	 * Do shutdown.
	 */
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

	/**
	 * Sets the game state.
	 *
	 * @param gameState
	 *            the new game state
	 */
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

	/**
	 * Checks if is game started.
	 *
	 * @return true, if is game started
	 */
	public boolean isGameStarted() {
		return gameState == GameState.INGAME;
	}

	/**
	 * Gets the game state.
	 *
	 * @return the game state
	 */
	public GameState getGameState() {
		return gameState;
	}

}
