package com.florianwoelki.minigameapi.command;

import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

/**
 * This class represents a Sender.
 * 
 * Specified for spigot senders.
 */
public enum Sender {

	/** The console. */
	CONSOLE(ConsoleCommandSender.class),

	/** The player. */
	PLAYER(Player.class),

	/** The everyone. */
	EVERYONE(CommandSender.class);

	private Class<?> senderClass;

	/**
	 * Instantiates a new sender.
	 *
	 * @param senderClass
	 *            the sender class
	 */
	Sender(Class<?> senderClass) {
		this.senderClass = senderClass;
	}

	/**
	 * Gets the sender class.
	 *
	 * @return the sender class
	 */
	public Class<?> getSenderClass() {
		return senderClass;
	}

}
