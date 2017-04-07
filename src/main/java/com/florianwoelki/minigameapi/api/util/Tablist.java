package com.florianwoelki.minigameapi.api.util;

import java.lang.reflect.Field;

import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerListHeaderFooter;
import net.minecraft.server.v1_8_R3.PlayerConnection;

/**
 * This class represents the spigot 1.8 tablist feature.
 */
public class Tablist {

	private String header;
	private String footer;

	/**
	 * Constructor with a specific header.
	 * 
	 * @param header
	 *            the header
	 */
	public Tablist(String header) {
		this.header = header;
	}

	/**
	 * Constructor with a specific header and footer.
	 * 
	 * @param header
	 *            the header
	 * @param footer
	 *            the footer
	 */
	public Tablist(String header, String footer) {
		this.header = header;
		this.footer = footer;
	}

	/**
	 * Set the tablist for a specific player.
	 * 
	 * @param player
	 *            the player who will see the tablist
	 */
	public void setTablist(Player player) {
		CraftPlayer craftPlayer = (CraftPlayer) player;
		PlayerConnection connection = craftPlayer.getHandle().playerConnection;
		IChatBaseComponent headerJSON = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + header + "\"}");
		IChatBaseComponent footerJSON = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + footer + "\"}");
		PacketPlayOutPlayerListHeaderFooter packet = new PacketPlayOutPlayerListHeaderFooter();

		try {
			Field headerField = packet.getClass().getDeclaredField("a");
			headerField.setAccessible(true);
			headerField.set(packet, headerJSON);
			headerField.setAccessible(!headerField.isAccessible());

			Field footerField = packet.getClass().getDeclaredField("b");
			footerField.setAccessible(true);
			footerField.set(packet, footerJSON);
			footerField.setAccessible(!footerField.isAccessible());
		} catch(Exception e) {
			e.printStackTrace();
		}

		connection.sendPacket(packet);
	}

	/**
	 * Set the header.
	 * 
	 * @param header
	 *            the header
	 */
	public void setHeader(String header) {
		this.header = header;
	}

	/**
	 * Get the header.
	 * 
	 * @return the header
	 */
	public String getHeader() {
		return header;
	}

	/**
	 * Set the footer.
	 * 
	 * @param footer
	 *            the footer
	 */
	public void setFooter(String footer) {
		this.footer = footer;
	}

	/**
	 * Get the footer.
	 * 
	 * @return the footer
	 */
	public String getFooter() {
		return footer;
	}

}
