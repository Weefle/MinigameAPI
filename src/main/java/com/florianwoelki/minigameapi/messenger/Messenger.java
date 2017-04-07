package com.florianwoelki.minigameapi.messenger;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

import com.florianwoelki.minigameapi.MinigameAPI;

/**
 * The Class Messenger.
 */
public class Messenger {

	/** The instance. */
	private static Messenger instance;

	/** The prefix. */
	private String prefix;

	/**
	 * Instantiates a new messenger.
	 */
	public Messenger() {
		prefix = "§6" + MinigameAPI.getInstance().getChatPrefix() + " §8▌ §r";
	}

	/**
	 * Message.
	 *
	 * @param commandSender
	 *            the command sender
	 * @param messageType
	 *            the message type
	 * @param messages
	 *            the messages
	 */
	public void message(CommandSender commandSender, MessageType messageType, String... messages) {
		for(String message : messages) {
			commandSender.sendMessage(prefix + messageType.getColor() + message);
		}
	}

	/**
	 * Broadcast.
	 *
	 * @param messageType
	 *            the message type
	 * @param messages
	 *            the messages
	 */
	public void broadcast(MessageType messageType, String... messages) {
		for(String message : messages) {
			Bukkit.broadcastMessage(prefix + messageType.getColor() + message);
		}
	}

	/**
	 * Space broadcast.
	 *
	 * @param messages
	 *            the messages
	 */
	public void spaceBroadcast(String... messages) {
		for(String message : messages) {
			Bukkit.broadcastMessage("         " + message);
		}
	}

	/**
	 * Sets the prefix.
	 *
	 * @param prefix
	 *            the new prefix
	 */
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	/**
	 * Gets the prefix.
	 *
	 * @return the prefix
	 */
	public String getPrefix() {
		return prefix;
	}

	/**
	 * Gets the single instance of Messenger.
	 *
	 * @return single instance of Messenger
	 */
	public static Messenger getInstance() {
		if(instance == null) {
			instance = new Messenger();
		}
		return instance;
	}

}
