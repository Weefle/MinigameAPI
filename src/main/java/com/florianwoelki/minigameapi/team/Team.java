package com.florianwoelki.minigameapi.team;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;

/**
 * The Class Team.
 */
public class Team {

	/** The name. */
	private final String name;

	/** The color. */
	private final Color color;

	/** The chat color. */
	private final ChatColor chatColor;

	/** The armor. */
	private ItemStack[] armor;

	/** The team size. */
	private int teamSize = 4;

	/** The team player locations. */
	private final List<TeamPlayerLocation> teamPlayerLocations;

	/** The armor stand location. */
	private Location armorStandLocation;

	/** The current armor stand. */
	private ArmorStand currentArmorStand;

	/**
	 * Instantiates a new team.
	 *
	 * @param name
	 *            the name
	 * @param color
	 *            the color
	 * @param chatColor
	 *            the chat color
	 */
	public Team(String name, Color color, ChatColor chatColor) {
		this.name = name;
		this.color = color;
		this.chatColor = chatColor;
		this.teamPlayerLocations = new ArrayList<>();
		createArmorItems();
	}

	/**
	 * Adds the player.
	 *
	 * @param player
	 *            the player
	 */
	public void addPlayer(Player player) {
		for(TeamPlayerLocation location : teamPlayerLocations) {
			if(location.getCurrentPlayer() == null) {
				location.setCurrentPlayer(player);
				break;
			}
		}
	}

	/**
	 * Removes the player.
	 *
	 * @param player
	 *            the player
	 */
	public void removePlayer(Player player) {
		TeamPlayerLocation location = getFakeLocation(player);
		if(location != null) {
			location.setCurrentPlayer(null);
		}
	}

	/**
	 * Creates the armor stand.
	 */
	public void createArmorStand() {
		if(currentArmorStand != null) {
			return;
		}

		currentArmorStand = ((ArmorStand) armorStandLocation.getWorld().spawn(armorStandLocation, ArmorStand.class));
		currentArmorStand.setArms(false);
		currentArmorStand.setBasePlate(false);

		currentArmorStand.setBoots(armor[0]);
		currentArmorStand.setLeggings(armor[1]);
		currentArmorStand.setChestplate(armor[2]);
		currentArmorStand.setHelmet(armor[3]);
	}

	/**
	 * Creates the armor items.
	 */
	private void createArmorItems() {
		armor = new ItemStack[4];
		armor[0] = new ItemStack(Material.LEATHER_BOOTS);
		armor[1] = new ItemStack(Material.LEATHER_LEGGINGS);
		armor[2] = new ItemStack(Material.LEATHER_CHESTPLATE);
		armor[3] = null;
		for(int i = 0; i < armor.length; i++) {
			ItemStack item = armor[i];
			if(item != null) {
				LeatherArmorMeta meta = (LeatherArmorMeta) item.getItemMeta();
				meta.setColor(color);
				item.setItemMeta(meta);
				armor[i] = item;
			}
		}
	}

	/**
	 * Gets the players.
	 *
	 * @return the players
	 */
	public List<Player> getPlayers() {
		List<Player> playerList = new ArrayList<>();
		for(TeamPlayerLocation location : teamPlayerLocations) {
			if(location.getCurrentPlayer() != null) {
				playerList.add(location.getCurrentPlayer());
			}
		}
		return playerList;
	}

	/**
	 * Gets the fake location.
	 *
	 * @param player
	 *            the player
	 * @return the fake location
	 */
	public TeamPlayerLocation getFakeLocation(Player player) {
		for(TeamPlayerLocation location : teamPlayerLocations) {
			if(location.getCurrentPlayer() == player) {
				return location;
			}
		}
		return null;
	}

	/**
	 * Sets the armor stand location.
	 *
	 * @param armorStandLocation
	 *            the new armor stand location
	 */
	public void setArmorStandLocation(Location armorStandLocation) {
		this.armorStandLocation = armorStandLocation;
	}

	/**
	 * Gets the armor stand location.
	 *
	 * @return the armor stand location
	 */
	public Location getArmorStandLocation() {
		return armorStandLocation;
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Gets the current armor stand.
	 *
	 * @return the current armor stand
	 */
	public ArmorStand getCurrentArmorStand() {
		return currentArmorStand;
	}

	/**
	 * Gets the team player locations.
	 *
	 * @return the team player locations
	 */
	public List<TeamPlayerLocation> getTeamPlayerLocations() {
		return teamPlayerLocations;
	}

	/**
	 * Gets the color.
	 *
	 * @return the color
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * Gets the chat color.
	 *
	 * @return the chat color
	 */
	public ChatColor getChatColor() {
		return chatColor;
	}

	/**
	 * Gets the armor.
	 *
	 * @return the armor
	 */
	public ItemStack[] getArmor() {
		return armor;
	}

	/**
	 * Sets the team size.
	 *
	 * @param teamSize
	 *            the new team size
	 */
	public void setTeamSize(int teamSize) {
		this.teamSize = teamSize;
	}

	/**
	 * Gets the team size.
	 *
	 * @return the team size
	 */
	public int getTeamSize() {
		return teamSize;
	}

}
