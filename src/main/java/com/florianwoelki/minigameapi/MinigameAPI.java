package com.florianwoelki.minigameapi;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.florianwoelki.minigameapi.achievement.AchievementManager;
import com.florianwoelki.minigameapi.api.Minigame;
import com.florianwoelki.minigameapi.api.StopReason;
import com.florianwoelki.minigameapi.api.util.ItemBuilder;
import com.florianwoelki.minigameapi.command.CommandHandler;
import com.florianwoelki.minigameapi.command.admin.CommandSetGameArea;
import com.florianwoelki.minigameapi.command.admin.CommandSetLobby;
import com.florianwoelki.minigameapi.command.admin.CommandStart;
import com.florianwoelki.minigameapi.command.admin.CommandWorldTeleport;
import com.florianwoelki.minigameapi.command.user.CommandAchievements;
import com.florianwoelki.minigameapi.command.user.CommandVoteMap;
import com.florianwoelki.minigameapi.config.Config;
import com.florianwoelki.minigameapi.database.DatabaseManager;
import com.florianwoelki.minigameapi.game.Game;
import com.florianwoelki.minigameapi.game.GameState;
import com.florianwoelki.minigameapi.game.GameTimer;
import com.florianwoelki.minigameapi.kit.Kit;
import com.florianwoelki.minigameapi.kit.KitManager;
import com.florianwoelki.minigameapi.listener.LobbyListener;
import com.florianwoelki.minigameapi.profile.ProfileManager;
import com.florianwoelki.minigameapi.skill.Skill;
import com.florianwoelki.minigameapi.skill.SkillManager;
import com.florianwoelki.minigameapi.spectator.SpectatorListener;
import com.florianwoelki.minigameapi.spectator.SpectatorManager;
import com.florianwoelki.minigameapi.team.TeamManager;
import com.florianwoelki.minigameapi.team.TeamScoreboardManager;
import com.florianwoelki.minigameapi.vote.VoteManager;

/**
 * The Class MinigameAPI.
 */
public class MinigameAPI extends JavaPlugin {

	/** The instance. */
	private static MinigameAPI instance;

	/** The managers. */
	private final Map<String, Manager> MANAGERS = new HashMap<>();

	/** The chat prefix. */
	private String chatPrefix;

	/** The minigame. */
	private Minigame minigame;

	/** The minigame name. */
	private String minigameName;

	/** The is block changes enabled. */
	private boolean isBlockChangesEnabled = false;

	/** The is allow spectator death. */
	private boolean isAllowSpectatorDeath = true;

	/** The command handler. */
	private CommandHandler commandHandler;

	/** The config. */
	private Config config;

	/** The game. */
	private Game game;

	/** The game timer. */
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

	/**
	 * Initialize minigame.
	 *
	 * @param minigame
	 *            the minigame
	 */
	public void initializeMinigame(Minigame minigame) {
		if(minigame == null) {
			throw new RuntimeException("Already initialized!");
		}

		this.minigame = minigame;
	}

	/**
	 * Enable map voting.
	 */
	public void enableMapVoting() {
		VoteManager voteManager = new VoteManager();
		addManager("map_voting", voteManager);
		commandHandler.register(CommandVoteMap.class, new CommandVoteMap(voteManager));
	}

	/**
	 * Enable kits.
	 *
	 * @param kits
	 *            the kits
	 */
	public void enableKits(Kit... kits) {
		addManager("kits", new KitManager(kits));
	}

	/**
	 * Enable skills.
	 *
	 * @param skills
	 *            the skills
	 */
	public void enableSkills(Skill... skills) {
		addManager("skills", new SkillManager(skills));
	}

	/**
	 * Enable database.
	 */
	public void enableDatabase() {
		addManager("database", new DatabaseManager());

		addManager("profile", new ProfileManager());
	}

	/**
	 * Enable achievements.
	 */
	public void enableAchievements() {
		AchievementManager achievementManager = new AchievementManager();
		addManager("achievements", achievementManager);
		commandHandler.register(CommandAchievements.class, new CommandAchievements(achievementManager));
	}

	/**
	 * Enable teams.
	 */
	public void enableTeams() {
		addManager("teams", new TeamManager());
	}

	/**
	 * Enable team scoreboard.
	 */
	public void enableTeamScoreboard() {
		addManager("team_scoreboard", new TeamScoreboardManager());
	}

	/**
	 * Gets the ingame players.
	 *
	 * @return the ingame players
	 */
	public List<Player> getIngamePlayers() {
		List<Player> players = new ArrayList<>();
		for(Player onlinePlayer : Bukkit.getOnlinePlayers()) {
			players.add(onlinePlayer);
		}

		players.removeAll(SpectatorManager.getSpectators());
		return players;
	}

	/**
	 * Request map.
	 *
	 * @param mapName
	 *            the map name
	 * @return the world
	 */
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

	/**
	 * Adds the manager.
	 *
	 * @param name
	 *            the name
	 * @param manager
	 *            the manager
	 */
	public void addManager(String name, Manager manager) {
		MANAGERS.put(name, manager);
		manager.onLoad();
	}

	/**
	 * Removes the manager.
	 *
	 * @param name
	 *            the name
	 */
	public void removeManager(String name) {
		Manager manager = (Manager) MANAGERS.get(name);

		if(manager != null) {
			manager.onUnload();
			MANAGERS.remove(name);
		}
	}

	/**
	 * Give spectator items.
	 *
	 * @param player
	 *            the player
	 * @param isJoined
	 *            the is joined
	 */
	public void giveSpectatorItems(Player player, boolean isJoined) {
		player.getInventory().setArmorContents(null);
		player.getInventory().setItem(0, new ItemBuilder(Material.COMPASS).setName("§7§oTeleporter").build());
		player.getInventory().setItem(8, new ItemBuilder(Material.MAGMA_CREAM).setName("§7Back to §eLobby").build());
		player.updateInventory();
	}

	/**
	 * Give lobby items.
	 *
	 * @param player
	 *            the player
	 */
	public void giveLobbyItems(Player player) {
		if(getManager("achievements") != null) {
			player.getInventory().setItem(0, new ItemBuilder(Material.WRITTEN_BOOK).setName("§c§lAchievements").build());
		}
		player.getInventory().setItem(8, new ItemBuilder(Material.MAGMA_CREAM).setName("§7Back to §alobby").build());
		player.updateInventory();
	}

	/**
	 * Gets the game.
	 *
	 * @return the game
	 */
	public Game getGame() {
		return game;
	}

	/**
	 * Gets the game timer.
	 *
	 * @return the game timer
	 */
	public GameTimer getGameTimer() {
		return gameTimer;
	}

	/**
	 * Gets the minigame config.
	 *
	 * @return the minigame config
	 */
	public Config getMinigameConfig() {
		return config;
	}

	/**
	 * Gets the command handler.
	 *
	 * @return the command handler
	 */
	public CommandHandler getCommandHandler() {
		return commandHandler;
	}

	/**
	 * Sets the block changes enabled.
	 *
	 * @param isBlockChangesEnabled
	 *            the new block changes enabled
	 */
	public void setBlockChangesEnabled(boolean isBlockChangesEnabled) {
		this.isBlockChangesEnabled = isBlockChangesEnabled;
	}

	/**
	 * Checks if is block changes enabled.
	 *
	 * @return true, if is block changes enabled
	 */
	public boolean isBlockChangesEnabled() {
		return isBlockChangesEnabled;
	}

	/**
	 * Gets the minigame.
	 *
	 * @return the minigame
	 */
	public Minigame getMinigame() {
		return minigame;
	}

	/**
	 * Sets the minigame name.
	 *
	 * @param minigameName
	 *            the new minigame name
	 */
	public void setMinigameName(String minigameName) {
		this.minigameName = minigameName;
	}

	/**
	 * Gets the minigame name.
	 *
	 * @return the minigame name
	 */
	public String getMinigameName() {
		return minigameName;
	}

	/**
	 * Sets the chat prefix.
	 *
	 * @param chatPrefix
	 *            the new chat prefix
	 */
	public void setChatPrefix(String chatPrefix) {
		this.chatPrefix = chatPrefix;
	}

	/**
	 * Gets the chat prefix.
	 *
	 * @return the chat prefix
	 */
	public String getChatPrefix() {
		return chatPrefix;
	}

	/**
	 * Sets the allow spectator death.
	 *
	 * @param isAllowSpectatorDeath
	 *            the new allow spectator death
	 */
	public void setAllowSpectatorDeath(boolean isAllowSpectatorDeath) {
		this.isAllowSpectatorDeath = isAllowSpectatorDeath;
	}

	/**
	 * Checks if is allow spectator death.
	 *
	 * @return true, if is allow spectator death
	 */
	public boolean isAllowSpectatorDeath() {
		return isAllowSpectatorDeath;
	}

	/**
	 * Gets the manager.
	 *
	 * @param name
	 *            the name
	 * @return the manager
	 */
	public Manager getManager(String name) {
		return (Manager) MANAGERS.get(name);
	}

	/**
	 * Gets the managers.
	 *
	 * @return the managers
	 */
	public Collection<Manager> getManagers() {
		return MANAGERS.values();
	}

	/**
	 * Gets the single instance of MinigameAPI.
	 *
	 * @return single instance of MinigameAPI
	 */
	public static MinigameAPI getInstance() {
		return instance;
	}

}
