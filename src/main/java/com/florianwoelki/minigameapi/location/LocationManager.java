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
