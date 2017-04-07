package com.florianwoelki.minigameapi.messenger;

import org.bukkit.ChatColor;

/**
 * The Enum MessageType.
 */
public enum MessageType {

	/** The info. */
	INFO(ChatColor.GRAY),

	/** The good. */
	GOOD(ChatColor.GREEN),

	/** The bad. */
	BAD(ChatColor.RED);

	/** The color. */
	private ChatColor color;

	/**
	 * Instantiates a new message type.
	 *
	 * @param color
	 *            the color
	 */
	MessageType(ChatColor color) {
		this.color = color;
	}

	/**
	 * Gets the color.
	 *
	 * @return the color
	 */
	public ChatColor getColor() {
		return color;
	}

}
