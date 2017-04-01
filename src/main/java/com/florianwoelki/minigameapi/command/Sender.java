package com.florianwoelki.minigameapi.command;

import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public enum Sender {

	CONSOLE(ConsoleCommandSender.class),

	PLAYER(Player.class),

	EVERYONE(CommandSender.class);

	private Class<?> senderClass;

	Sender(Class<?> senderClass) {
		this.senderClass = senderClass;
	}

	public Class<?> getSenderClass() {
		return senderClass;
	}

}
