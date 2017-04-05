package com.florianwoelki.minigameapi;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.florianwoelki.minigameapi.api.Minigame;
import com.florianwoelki.minigameapi.api.StopReason;
import com.florianwoelki.minigameapi.api.util.ItemBuilder;
import com.florianwoelki.minigameapi.command.CommandHandler;
import com.florianwoelki.minigameapi.command.admin.CommandSetGameArea;
import com.florianwoelki.minigameapi.command.admin.CommandSetLobby;
import com.florianwoelki.minigameapi.command.admin.CommandStart;
import com.florianwoelki.minigameapi.command.admin.CommandWorldTeleport;
import com.florianwoelki.minigameapi.command.user.CommandVoteMap;
import com.florianwoelki.minigameapi.config.Config;
import com.florianwoelki.minigameapi.database.DatabaseManager;
import com.florianwoelki.minigameapi.game.Game;
import com.florianwoelki.minigameapi.game.GameState;
import com.florianwoelki.minigameapi.game.GameTimer;
import com.florianwoelki.minigameapi.kit.Kit;
import com.florianwoelki.minigameapi.kit.KitManager;
import com.florianwoelki.minigameapi.listener.LobbyListener;
import com.florianwoelki.minigameapi.player.PlayerWrapper;
import com.florianwoelki.minigameapi.skill.Skill;
import com.florianwoelki.minigameapi.skill.SkillManager;
import com.florianwoelki.minigameapi.spectator.SpectatorListener;
import com.florianwoelki.minigameapi.spectator.SpectatorManager;
import com.florianwoelki.minigameapi.team.TeamManager;
import com.florianwoelki.minigameapi.team.TeamScoreboardManager;
import com.florianwoelki.minigameapi.vote.VoteManager;

public class MinigameAPI extends JavaPlugin {

	private static MinigameAPI instance;

	private final Map<String, Manager> MANAGERS = new HashMap<>();

	private String chatPrefix;

	private Minigame minigame;
	private String minigameName;

	private boolean isBlockChangesEnabled = false;
	private boolean isAllowSpectatorDeath = true;

	private CommandHandler commandHandler;

	private Config config;

	private Game game;
	private GameTimer gameTimer;

	@Override
	public void onEnable() {
		instance = this;

		config = new Config();
		if(!config.getConfigFile().exists()) {
			config.save();
		}
		config.load();

		commandHandler = new CommandHandler(this);
		commandHandler.register(CommandStart.class, new CommandStart());
		commandHandler.register(CommandSetLobby.class, new CommandSetLobby());
		commandHandler.register(CommandWorldTeleport.class, new CommandWorldTeleport());
		commandHandler.register(CommandSetGameArea.class, new CommandSetGameArea());

		game = new Game();
		game.setGameState(GameState.LOBBY_WITH_NOY_PLAYERS);

		gameTimer = new GameTimer();
		gameTimer.resetTime();
		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, gameTimer, 20L, 20L);

		getServer().getPluginManager().registerEvents(new SpectatorListener(), this);
		getServer().getPluginManager().registerEvents(new LobbyListener(), this);
		getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
	}

	@Override
	public void onDisable() {
		instance = null;
		game.stopGame(StopReason.SERVER_STOP);
		game.setGameState(GameState.NONE);

		for(World world : Bukkit.getWorlds()) {
			Bukkit.unloadWorld(world, false);
			System.out.println("[MinigameAPI] Unloaded Map " + world.getName()); // TODO: Add custom logger.
		}
	}

	public void initializeMinigame(Minigame minigame) {
		if(minigame == null) {
			throw new RuntimeException("Already initialized!");
		}

		this.minigame = minigame;
	}

	public void enableMapVoting() {
		VoteManager voteManager = new VoteManager();
		addManager("map_voting", voteManager);
		commandHandler.register(CommandVoteMap.class, new CommandVoteMap(voteManager));
	}

	public void enableKits(Kit... kits) {
		addManager("kits", new KitManager(kits));
	}

	public void enableSkills(Skill... skills) {
		addManager("skills", new SkillManager(skills));
	}

	public void enableDatabase() {
		addManager("database", new DatabaseManager());
	}

	public void enableTeams() {
		addManager("teams", new TeamManager());
	}

	public void enableTeamScoreboard() {
		addManager("team_scoreboard", new TeamScoreboardManager());
	}

	public List<Player> getIngamePlayers() {
		List<Player> players = new ArrayList<>();
		for(Player onlinePlayer : Bukkit.getOnlinePlayers()) {
			players.add(onlinePlayer);
		}

		players.removeAll(SpectatorManager.getSpectators());
		return players;
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
		manager.onLoad();
	}

	public void removeManager(String name) {
		Manager manager = (Manager) MANAGERS.get(name);

		if(manager != null) {
			manager.onUnload();
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

	public void giveSpectatorItems(Player player, boolean isJoined) {
		player.getInventory().setArmorContents(null);
		player.getInventory().setItem(0, new ItemBuilder(Material.COMPASS).setName("§7§oTeleporter").build());
		player.getInventory().setItem(8, new ItemBuilder(Material.MAGMA_CREAM).setName("§7Zurück zur §eLobby").build());
		player.updateInventory();
	}

	public void giveLobbyItems(Player player) {
		player.getInventory().setItem(8, new ItemBuilder(Material.MAGMA_CREAM).setName("§7Back to §alobby").build());
		player.updateInventory();
	}

	public Game getGame() {
		return game;
	}

	public GameTimer getGameTimer() {
		return gameTimer;
	}

	public Config getMinigameConfig() {
		return config;
	}

	public CommandHandler getCommandHandler() {
		return commandHandler;
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
