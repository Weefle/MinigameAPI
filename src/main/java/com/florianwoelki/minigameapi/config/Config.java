package com.florianwoelki.minigameapi.config;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.florianwoelki.minigameapi.MinigameAPI;

/**
 * This class represents the config file
 * 
 * Name: config.yml
 */
public class Config {

	private File configFile;
	private FileConfiguration configuration;

	/**
	 * Empty constructor
	 * 
	 * Will setup the configs
	 */
	public Config() {
		this.configFile = new File(MinigameAPI.getInstance().getDataFolder(), "config.yml");
		this.configuration = YamlConfiguration.loadConfiguration(configFile);
	}

	/**
	 * Load the config data
	 */
	public void load() {
		ConfigData.minimumPlayers = configuration.getInt("MinimumPlayers");
		ConfigData.lobbyCountdown = configuration.getInt("Countdown.Lobby");
		ConfigData.voteCountdown = configuration.getInt("Countdown.MapVote");
		ConfigData.gameDuration = configuration.getInt("GameDuration");
		ConfigData.lobbyTime = ConfigData.voteCountdown + ConfigData.lobbyCountdown;

		ConfigData.host = configuration.getString("MySQL.Host");
		ConfigData.port = configuration.getInt("MySQL.Port");
		ConfigData.database = configuration.getString("MySQL.Database");
		ConfigData.username = configuration.getString("MySQL.Username");
		ConfigData.password = configuration.getString("MySQL.Password");
	}

	/**
	 * Save the config data
	 */
	public void save() {
		configuration.set("MinimumPlayers", ConfigData.minimumPlayers);
		configuration.set("Countdown.Lobby", ConfigData.lobbyCountdown);
		configuration.set("Countdown.MapVote", ConfigData.voteCountdown);
		configuration.set("GameDuration", ConfigData.gameDuration);

		configuration.set("MySQL.Host", "127.0.0.1");
		configuration.set("MySQL.Port", 3306);
		configuration.set("MySQL.Database", "MinigameAPI");
		configuration.set("MySQL.Username", "root");
		configuration.set("MySQL.Password", "");

		try {
			configuration.save(configFile);
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Get the config file
	 * 
	 * @return the config file
	 */
	public File getConfigFile() {
		return configFile;
	}

	/**
	 * Get the configuration
	 * 
	 * @return the configuration of the file
	 */
	public FileConfiguration getConfiguration() {
		return configuration;
	}

}
