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

public class Team {

	private final String name;

	private final Color color;
	private final ChatColor chatColor;
	private ItemStack[] armor;

	private int teamSize = 4;

	private final List<TeamPlayerLocation> teamPlayerLocations;
	private Location armorStandLocation;
	private ArmorStand currentArmorStand;

	public Team(String name, Color color, ChatColor chatColor) {
		this.name = name;
		this.color = color;
		this.chatColor = chatColor;
		this.teamPlayerLocations = new ArrayList<>();
		createArmorItems();
	}

	public void addPlayer(Player player) {
		for(TeamPlayerLocation location : teamPlayerLocations) {
			if(location.getCurrentPlayer() == null) {
				location.setCurrentPlayer(player);
				break;
			}
		}
	}

	public void removePlayer(Player player) {
		TeamPlayerLocation location = getFakeLocation(player);
		if(location != null) {
			location.setCurrentPlayer(null);
		}
	}

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

	public List<Player> getPlayers() {
		List<Player> playerList = new ArrayList<>();
		for(TeamPlayerLocation location : teamPlayerLocations) {
			if(location.getCurrentPlayer() != null) {
				playerList.add(location.getCurrentPlayer());
			}
		}
		return playerList;
	}

	public TeamPlayerLocation getFakeLocation(Player player) {
		for(TeamPlayerLocation location : teamPlayerLocations) {
			if(location.getCurrentPlayer() == player) {
				return location;
			}
		}
		return null;
	}

	public void setArmorStandLocation(Location armorStandLocation) {
		this.armorStandLocation = armorStandLocation;
	}

	public Location getArmorStandLocation() {
		return armorStandLocation;
	}

	public String getName() {
		return name;
	}

	public ArmorStand getCurrentArmorStand() {
		return currentArmorStand;
	}

	public List<TeamPlayerLocation> getTeamPlayerLocations() {
		return teamPlayerLocations;
	}

	public Color getColor() {
		return color;
	}

	public ChatColor getChatColor() {
		return chatColor;
	}

	public ItemStack[] getArmor() {
		return armor;
	}

	public void setTeamSize(int teamSize) {
		this.teamSize = teamSize;
	}

	public int getTeamSize() {
		return teamSize;
	}

}
