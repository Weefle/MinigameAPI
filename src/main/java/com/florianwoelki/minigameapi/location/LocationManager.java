package com.florianwoelki.minigameapi.location;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.florianwoelki.minigameapi.MinigameAPI;

public class LocationManager {

	private static LocationManager instance;

	private File file;
	private FileConfiguration configuration;

	public LocationManager() {
		this.file = new File(MinigameAPI.getInstance().getDataFolder() + "/location", "locations.yml");
		this.configuration = YamlConfiguration.loadConfiguration(file);
	}

	public void setBlockLocation(Location location, String path) {
		String world = location.getWorld().getName();
		double x = location.getX();
		double y = location.getY();
		double z = location.getZ();

		configuration.set(path, world + "," + x + "," + y + "," + z);

		save();
	}

	public void setLocation(Location location, String path) {
		String world = location.getWorld().getName();
		double x = location.getX();
		double y = location.getY();
		double z = location.getZ();
		double yaw = location.getYaw();
		double pitch = location.getPitch();

		configuration.set(path, world + "," + x + "," + y + "," + z + "," + yaw + "," + pitch);

		save();
	}

	public Location getBlockLocation(String path) {
		String locationString = configuration.getString(path);
		World world = Bukkit.getWorld(locationString.split(",")[0]);
		double x = Double.valueOf(locationString.split(",")[1]);
		double y = Double.valueOf(locationString.split(",")[2]);
		double z = Double.valueOf(locationString.split(",")[3]);
		return new Location(world, x, y, z);
	}

	public Location getLocation(String path) {
		if(configuration.getString(path) != null) {
			String locationString = configuration.getString(path);
			World world = Bukkit.getWorld(locationString.split(",")[0]);
			double x = Double.valueOf(locationString.split(",")[1]);
			double y = Double.valueOf(locationString.split(",")[2]);
			double z = Double.valueOf(locationString.split(",")[3]);
			double yaw = Double.valueOf(locationString.split(",")[4]);
			double pitch = Double.valueOf(locationString.split(",")[5]);
			return new Location(world, x, y, z, (float) yaw, (float) pitch);
		}
		return null;
	}

	public boolean isInsideLocation(Location location, String path) {
		Location loc1 = getBlockLocation(path + ".Pos1");
		Location loc2 = getBlockLocation(path + ".Pos2");

		if(loc1.getWorld() != loc2.getWorld()) {
			return false;
		}

		int minX = Math.min(loc1.getBlockX(), loc2.getBlockX());
		int minY = Math.min(loc1.getBlockY(), loc2.getBlockY());
		int minZ = Math.min(loc1.getBlockZ(), loc2.getBlockZ());

		int maxX = Math.max(loc1.getBlockX(), loc2.getBlockX());
		int maxY = Math.max(loc1.getBlockY(), loc2.getBlockY());
		int maxZ = Math.max(loc1.getBlockZ(), loc2.getBlockZ());

		if(location.getBlockX() >= minX && location.getBlockX() <= maxX && location.getBlockY() >= minY && location.getBlockY() <= maxY && location.getBlockZ() >= minZ && location.getBlockZ() <= maxZ) {
			return true;
		}
		return false;
	}

	public void save() {
		try {
			configuration.save(file);
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

	public FileConfiguration getConfiguration() {
		return configuration;
	}

	public static LocationManager getInstance() {
		if(instance == null) {
			instance = new LocationManager();
		}
		return instance;
	}

}
