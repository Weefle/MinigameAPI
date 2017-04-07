package com.florianwoelki.minigameapi.api.util;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle;

/**
 * This class represents the spigot 1.8 title feature.
 */
public class Title {

	private String title;
	private String subtitle;

	private int fadeInTime;
	private int stayTime;
	private int fadeOutTime;

	/**
	 * Constructor with a specific title.
	 * 
	 * @param title
	 *            the title
	 */
	public Title(String title) {
		this(title, "", 1, 1, 1);
	}

	/**
	 * Constructor with a specific title and subtitle.
	 * 
	 * @param title
	 *            the title
	 * @param subtitle
	 *            the subtitle
	 */
	public Title(String title, String subtitle) {
		this(title, subtitle, 1, 1, 1);
	}

	/**
	 * Constructor with a specific title, subtitle, fadeInTime, stayTime and fadeOutTime.
	 * 
	 * fadeInTime = in seconds stayTime = in seconds fadeOutTime = in seconds
	 * 
	 * @param title
	 *            the title
	 * @param subtitle
	 *            the subtitle
	 * @param fadeInTime
	 *            the fade in time
	 * @param stayTime
	 *            the stay time
	 * @param fadeOutTime
	 *            the fade out time
	 */
	public Title(String title, String subtitle, int fadeInTime, int stayTime, int fadeOutTime) {
		this.title = title;
		this.subtitle = subtitle;
		this.fadeInTime = fadeInTime;
		this.stayTime = stayTime;
		this.fadeOutTime = fadeOutTime;
	}

	/**
	 * Send the title to all online players.
	 */
	public void send() {
		for(Player onlinePlayer : Bukkit.getOnlinePlayers()) {
			send(onlinePlayer);
		}
	}

	/**
	 * Send the title a specific player.
	 * 
	 * @param player
	 *            the player who will receive the title
	 */
	public void send(Player player) {
		PacketPlayOutTitle timesPacket = new PacketPlayOutTitle(fadeInTime * 20, stayTime * 20, fadeOutTime * 20);
		PacketPlayOutTitle titlePacket = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + title + "\"}"));
		PacketPlayOutTitle subTitlePacket = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + subtitle + "\"}"));

		((CraftPlayer) player).getHandle().playerConnection.sendPacket(timesPacket);
		((CraftPlayer) player).getHandle().playerConnection.sendPacket(subTitlePacket);
		((CraftPlayer) player).getHandle().playerConnection.sendPacket(titlePacket);
	}

	/**
	 * Set the title.
	 * 
	 * @param title
	 *            the title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Get the title.
	 * 
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Set the subtitle.
	 * 
	 * @param subtitle
	 *            the subtitle
	 */
	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}

	/**
	 * Get the subtitle.
	 * 
	 * @return the subtitle
	 */
	public String getSubtitle() {
		return subtitle;
	}

	/**
	 * Set the fadeInTime.
	 * 
	 * @param fadeInTime
	 *            the fade in time
	 */
	public void setFadeInTime(int fadeInTime) {
		this.fadeInTime = fadeInTime;
	}

	/**
	 * Get the fadeInTime.
	 * 
	 * @return the fade in time
	 */
	public int getFadeInTime() {
		return fadeInTime;
	}

	/**
	 * Set the stayTime.
	 * 
	 * @param stayTime
	 *            the stay time
	 */
	public void setStayTime(int stayTime) {
		this.stayTime = stayTime;
	}

	/**
	 * Get the stayTime.
	 * 
	 * @return the stay time
	 */
	public int getStayTime() {
		return stayTime;
	}

	/**
	 * Set the fadeOutTime.
	 * 
	 * @param fadeOutTime
	 *            the fade out time
	 */
	public void setFadeOutTime(int fadeOutTime) {
		this.fadeOutTime = fadeOutTime;
	}

	/**
	 * Get the fadeOutTime.
	 * 
	 * @return the fade out time
	 */
	public int getFadeOutTime() {
		return fadeOutTime;
	}

}
