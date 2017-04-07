package com.florianwoelki.minigameapi.api;

/**
 * This interface represents a basic Minigame. It contains of two main methods which are called when the game starts and stops.
 */
public interface Minigame {

	/**
	 * This method will be called when the game starts.
	 */
	void startGame();

	/**
	 * This method will be called when the game stops.
	 * 
	 * @param stopReason
	 *            the stop reason for stopping the game
	 */
	void stopGame(StopReason stopReason);

}
