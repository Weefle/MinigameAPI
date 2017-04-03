package com.florianwoelki.minigameapi.api.util;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;

public class ActionBar {

	private String message;

	public ActionBar() {
	}

	public ActionBar(String message) {
		this.message = message;
	}

	public void send() {
		for(Player onlinePlayer : Bukkit.getOnlinePlayers()) {
			send(onlinePlayer);
		}
	}

	public void send(Player player) {
		IChatBaseComponent messageJson = IChatBaseComponent.ChatSerializer.a("{'text': '" + message + "'}");
		PacketPlayOutChat packet = new PacketPlayOutChat(messageJson, (byte) 2);
		((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

}
