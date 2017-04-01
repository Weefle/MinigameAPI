package com.florianwoelki.minigameapi.api.util;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle;

public class Title {

	private String title;
	private String subtitle;

	private int fadeInTime;
	private int stayTime;
	private int fadeOutTime;

	public Title(String title) {
		this(title, "", 1, 1, 1);
	}

	public Title(String title, String subtitle) {
		this(title, subtitle, 1, 1, 1);
	}

	public Title(String title, String subtitle, int fadeInTime, int stayTime, int fadeOutTime) {
		this.title = title;
		this.subtitle = subtitle;
		this.fadeInTime = fadeInTime;
		this.stayTime = stayTime;
		this.fadeOutTime = fadeOutTime;
	}

	public void send() {
		for(Player onlinePlayer : Bukkit.getOnlinePlayers()) {
			send(onlinePlayer);
		}
	}

	public void send(Player player) {
		PacketPlayOutTitle timesPacket = new PacketPlayOutTitle(fadeInTime * 20, stayTime * 20, fadeOutTime * 20);
		PacketPlayOutTitle titlePacket = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + title + "\"}"));
		PacketPlayOutTitle subTitlePacket = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + subtitle + "\"}"));

		((CraftPlayer) player).getHandle().playerConnection.sendPacket(timesPacket);
		((CraftPlayer) player).getHandle().playerConnection.sendPacket(subTitlePacket);
		((CraftPlayer) player).getHandle().playerConnection.sendPacket(titlePacket);
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}

	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}

	public String getSubtitle() {
		return subtitle;
	}

	public void setFadeInTime(int fadeInTime) {
		this.fadeInTime = fadeInTime;
	}

	public int getFadeInTime() {
		return fadeInTime;
	}

	public void setStayTime(int stayTime) {
		this.stayTime = stayTime;
	}

	public int getStayTime() {
		return stayTime;
	}

	public void setFadeOutTime(int fadeOutTime) {
		this.fadeOutTime = fadeOutTime;
	}

	public int getFadeOutTime() {
		return fadeOutTime;
	}

}
