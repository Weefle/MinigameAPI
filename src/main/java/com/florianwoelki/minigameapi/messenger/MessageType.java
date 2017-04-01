package com.florianwoelki.minigameapi.messenger;

import org.bukkit.ChatColor;

public enum MessageType {

	INFO(ChatColor.GRAY),

	GOOD(ChatColor.GREEN),

	BAD(ChatColor.RED);

	private ChatColor color;

	MessageType(ChatColor color) {
		this.color = color;
	}

	public ChatColor getColor() {
		return color;
	}

}
