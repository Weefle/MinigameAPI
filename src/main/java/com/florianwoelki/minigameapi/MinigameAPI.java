package com.florianwoelki.minigameapi;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.florianwoelki.minigameapi.api.Minigame;
import com.florianwoelki.minigameapi.database.DatabaseManager;
import com.florianwoelki.minigameapi.player.PlayerWrapper;

public class MinigameAPI extends JavaPlugin {

	private static MinigameAPI instance;

	private final Map<String, Manager> MANAGERS = new HashMap<>();

	private String chatPrefix;

	private Minigame minigame;
	private String minigameName;

	private boolean isBlockChangesEnabled = false;
	private boolean isAllowSpectatorDeath = true;

	@Override
	public void onEnable() {
		instance = this;

		getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
	}

	@Override
	public void onDisable() {
		instance = null;

		for(World world : Bukkit.getWorlds()) {
			Bukkit.unloadWorld(world, false);
			System.out.println("[MinigameAPI] Unloaded Map " + world.getName()); // TODO: Add custom logger.
		}
	}

	public void initializeMinigame(Minigame minigame) {
		if(minigame != null) {
			throw new RuntimeException("Already initialized!");
		}

		this.minigame = minigame;
	}

	public void enableDatabase() {
		addManager("database", new DatabaseManager());
	}

	public World requestMap(String mapName) {
		if(Bukkit.getWorld(mapName) != null) {
			return Bukkit.getWorld(mapName);
		}

		WorldCreator worldCreator = new WorldCreator(mapName);
		worldCreator.generateStructures(false);

		World world = worldCreator.createWorld();
		world.setAutoSave(false);
		world.setDifficulty(Difficulty.NORMAL);
		world.setSpawnFlags(false, false);

		return world;
	}

	public void addManager(String name, Manager manager) {
		MANAGERS.put(name, manager);
		manager.load();
	}

	public void removeManager(String name) {
		Manager manager = (Manager) MANAGERS.get(name);

		if(manager != null) {
			manager.unload();
			MANAGERS.remove(name);
		}
	}

	public PlayerWrapper getOnlinePlayer(String name) {
		for(Player onlinePlayer : Bukkit.getOnlinePlayers()) {
			if(onlinePlayer.getName().equalsIgnoreCase(name)) {
				return (PlayerWrapper) onlinePlayer;
			}
		}
		return null;
	}

	public PlayerWrapper getOnlinePlayer(UUID uuid) {
		for(Player onlinePlayer : Bukkit.getOnlinePlayers()) {
			if(onlinePlayer.getUniqueId().equals(onlinePlayer.getUniqueId())) {
				return (PlayerWrapper) onlinePlayer;
			}
		}
		return null;
	}

	public void setBlockChangesEnabled(boolean isBlockChangesEnabled) {
		this.isBlockChangesEnabled = isBlockChangesEnabled;
	}

	public boolean isBlockChangesEnabled() {
		return isBlockChangesEnabled;
	}

	public Minigame getMinigame() {
		return minigame;
	}

	public void setMinigameName(String minigameName) {
		this.minigameName = minigameName;
	}

	public String getMinigameName() {
		return minigameName;
	}

	public void setChatPrefix(String chatPrefix) {
		this.chatPrefix = chatPrefix;
	}

	public String getChatPrefix() {
		return chatPrefix;
	}

	public void setAllowSpectatorDeath(boolean isAllowSpectatorDeath) {
		this.isAllowSpectatorDeath = isAllowSpectatorDeath;
	}

	public boolean isAllowSpectatorDeath() {
		return isAllowSpectatorDeath;
	}

	public Manager getManager(String name) {
		return (Manager) MANAGERS.get(name);
	}

	public Collection<Manager> getManagers() {
		return MANAGERS.values();
	}

	public static MinigameAPI getInstance() {
		return instance;
	}

}
