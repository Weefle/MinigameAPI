package com.florianwoelki.minigameapi.messenger;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

import com.florianwoelki.minigameapi.MinigameAPI;

public class Messenger {

	private static Messenger instance;

	private String prefix;

	public Messenger() {
		prefix = "§6" + MinigameAPI.getInstance().getChatPrefix() + " §8▌ §r";
	}

	public void message(CommandSender commandSender, MessageType messageType, String... messages) {
		for(String message : messages) {
			commandSender.sendMessage(prefix + messageType.getColor() + message);
		}
	}

	public void broadcast(MessageType messageType, String... messages) {
		for(String message : messages) {
			Bukkit.broadcastMessage(prefix + messageType.getColor() + message);
		}
	}

	public void spaceBroadcast(String... messages) {
		for(String message : messages) {
			Bukkit.broadcastMessage("         " + message);
		}
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public String getPrefix() {
		return prefix;
	}

	public static Messenger getInstance() {
		if(instance == null) {
			instance = new Messenger();
		}
		return instance;
	}

}
