package com.florianwoelki.minigameapi.api.util;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;

/**
 * This class represents the spigot 1.8 ActionBar feature.
 */
public class ActionBar {

	private String message;

	/**
	 * Empty constructor.
	 */
	public ActionBar() {
	}

	/**
	 * ActionBar constructor with a specific message.
	 * 
	 * @param message
	 *            the message which will be displayed for the ActionBar
	 */
	public ActionBar(String message) {
		this.message = message;
	}

	/**
	 * Send all online players the ActionBar.
	 */
	public void send() {
		for(Player onlinePlayer : Bukkit.getOnlinePlayers()) {
			send(onlinePlayer);
		}
	}

	/**
	 * Send a specific player the ActionBar.
	 * 
	 * @param player
	 *            the player who will receive the ActionBar
	 */
	public void send(Player player) {
		IChatBaseComponent messageJson = IChatBaseComponent.ChatSerializer.a("{'text': '" + message + "'}");
		PacketPlayOutChat packet = new PacketPlayOutChat(messageJson, (byte) 2);
		((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
	}

	/**
	 * Set the message of the ActionBar.
	 * 
	 * @param message
	 *            the message for the ActionBar
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * Get the message of the ActionBar.
	 * 
	 * @return the message of the ActionBar
	 */
	public String getMessage() {
		return message;
	}

}
