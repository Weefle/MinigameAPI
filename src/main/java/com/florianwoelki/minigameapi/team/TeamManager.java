package com.florianwoelki.minigameapi.team;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.BlockFace;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

import com.florianwoelki.minigameapi.Manager;
import com.florianwoelki.minigameapi.MinigameAPI;
import com.florianwoelki.minigameapi.api.StopReason;
import com.florianwoelki.minigameapi.messenger.MessageType;
import com.florianwoelki.minigameapi.messenger.Messenger;
import com.florianwoelki.minigameapi.team.event.PlayerSelectTeamEvent;
import com.florianwoelki.minigameapi.team.listener.TeamListener;
import com.florianwoelki.minigameapi.util.BukkitUtils;

/**
 * The Class TeamManager.
 */
public class TeamManager extends Manager {

	/** The teams. */
	private final List<Team> teams = new ArrayList<>();

	/** The team size. */
	private int teamSize;

	/** The listener. */
	private TeamListener listener;

	/** The player team map. */
	private final Map<Player, Team> playerTeamMap = new HashMap<>();

	@Override
	public void onLoad() {
		listener = new TeamListener(this);
		Bukkit.getPluginManager().registerEvents(listener, MinigameAPI.getInstance());

		loadConfig();
	}

	@Override
	public void onUnload() {
		HandlerList.unregisterAll(listener);
		for(Team team : teams) {
			team.getCurrentArmorStand().remove();
		}
	}

	/**
	 * Leave player.
	 *
	 * @param player
	 *            the player
	 */
	public void leavePlayer(Player player) {
		setTeam(player, null);

		if(MinigameAPI.getInstance().getGame().isGameStarted() && getTeamsWithPlayers().size() <= 1) {
			MinigameAPI.getInstance().getGame().stopGame(StopReason.NO_PLAYERS);
		}
	}

	/**
	 * Start game.
	 */
	public void startGame() {
		assignTeams();
	}

	/**
	 * Assign teams.
	 */
	public void assignTeams() {
		List<Player> playersWithNoTeams = new ArrayList<>();
		playersWithNoTeams.addAll(MinigameAPI.getInstance().getIngamePlayers());
		playersWithNoTeams.removeAll(playerTeamMap.keySet());

		Collections.shuffle(playersWithNoTeams);

		if(playersWithNoTeams.isEmpty()) {
			return;
		}

		List<Team> teamsWithPlayers = new ArrayList<>();
		for(Team team : teams) {
			if(!team.getPlayers().isEmpty()) {
				teamsWithPlayers.add(team);
			}
		}

		while(teamsWithPlayers.size() < 2 && !playersWithNoTeams.isEmpty()) {
			Player player = (Player) playersWithNoTeams.get(0);
			playersWithNoTeams.remove(0);

			Team team = getTeamWithLowestMembers();
			setTeam(player, team);
			Messenger.getInstance().message(player, MessageType.GOOD, "You are in team " + team.getChatColor() + team.getName() + ChatColor.GOLD + "!");
			if(!teamsWithPlayers.contains(team)) {
				teamsWithPlayers.add(team);
			}
		}

		if(teamsWithPlayers.isEmpty()) {
			return;
		}

		for(Team team : teams) {
			while(team.getPlayers().size() < 2 && !playersWithNoTeams.isEmpty()) {
				Player player = (Player) playersWithNoTeams.get(0);
				playersWithNoTeams.remove(0);

				setTeam(player, team);
				Messenger.getInstance().message(player, MessageType.INFO, "You are in team " + team.getChatColor() + team.getName() + ChatColor.GOLD + "!");
			}
		}

		if(teamsWithPlayers.isEmpty()) {
			return;
		}

		while(!playersWithNoTeams.isEmpty()) {
			Player player = (Player) playersWithNoTeams.get(0);
			playersWithNoTeams.remove(0);

			Team team = getTeamWithLowestMembers();
			setTeam(player, team);
			Messenger.getInstance().message(player, MessageType.INFO, "You are in team " + team.getChatColor() + team.getName() + ChatColor.GOLD + "!");
		}
	}

	/**
	 * Load config.
	 */
	public void loadConfig() {
		YamlConfiguration config = YamlConfiguration.loadConfiguration(new File(MinigameAPI.getInstance().getDataFolder(), "teams.yml"));
		for(String teamName : config.getKeys(false)) {
			ConfigurationSection section = config.getConfigurationSection(teamName);

			Color color = BukkitUtils.getColorFromName(section.getString("Color").toUpperCase());
			ChatColor chatColor = ChatColor.valueOf(section.getString("ChatColor").toUpperCase());

			Team team = new Team(teamName, color, chatColor);
			team.setTeamSize(section.getInt("TeamSize"));

			List<String> playerLocationsList = section.getStringList("PlayerLocations");
			for(String s : playerLocationsList) {
				String[] split = s.split(";");
				double x = Double.parseDouble(split[0]);
				double y = Double.parseDouble(split[1]);
				double z = Double.parseDouble(split[2]);
				BlockFace blockFace = BlockFace.valueOf(split[3].toUpperCase());

				TeamPlayerLocation location = new TeamPlayerLocation(team, x, y, z, blockFace);
				team.getTeamPlayerLocations().add(location);
			}

			ConfigurationSection armorStandLocationSection = section.getConfigurationSection("ArmorStand");

			double x = armorStandLocationSection.getDouble("X");
			double y = armorStandLocationSection.getDouble("Y");
			double z = armorStandLocationSection.getDouble("Z");
			BlockFace blockFace = BlockFace.valueOf(armorStandLocationSection.getString("Face").toUpperCase());

			Location location = new Location((World) Bukkit.getWorlds().get(0), x, y, z, faceToYaw(blockFace), 0.0f);
			team.setArmorStandLocation(location);

			team.createArmorStand();
			teams.add(team);
		}
	}

	/**
	 * Gets the team with lowest members.
	 *
	 * @return the team with lowest members
	 */
	public Team getTeamWithLowestMembers() {
		int lowestCount = Integer.MAX_VALUE;
		Team lowestTeam = null;

		List<Team> allTeams = new ArrayList<>();
		allTeams.addAll(teams);
		Collections.shuffle(allTeams);
		for(Team team : allTeams) {
			int playerCount = team.getPlayers().size();
			if(playerCount < lowestCount) {
				lowestCount = playerCount;
				lowestTeam = team;
			}
		}

		return lowestTeam;
	}

	/**
	 * Gets the teams with players.
	 *
	 * @return the teams with players
	 */
	public List<Team> getTeamsWithPlayers() {
		List<Team> teamsWithPlayers = new ArrayList<>();
		for(Team team : teams) {
			if(!team.getPlayers().isEmpty()) {
				teamsWithPlayers.add(team);
			}
		}

		return teamsWithPlayers;
	}

	/**
	 * Gets the team from armor stand.
	 *
	 * @param armorStand
	 *            the armor stand
	 * @return the team from armor stand
	 */
	public Team getTeamFromArmorStand(ArmorStand armorStand) {
		for(Team team : teams) {
			if(team.getCurrentArmorStand() != null && team.getCurrentArmorStand().equals(armorStand)) {
				return team;
			}
		}

		return null;
	}

	/**
	 * Gets the players in team.
	 *
	 * @param team
	 *            the team
	 * @return the players in team
	 */
	public List<Player> getPlayersInTeam(Team team) {
		List<Player> players = new ArrayList<>();
		for(Map.Entry<Player, Team> e : playerTeamMap.entrySet()) {
			if(e.getValue() == team) {
				players.add(e.getKey());
			}
		}

		return players;
	}

	/**
	 * Gets the team from player.
	 *
	 * @param player
	 *            the player
	 * @return the team from player
	 */
	public Team getTeamFromPlayer(Player player) {
		return (Team) playerTeamMap.get(player);
	}

	/**
	 * Sets the team.
	 *
	 * @param player
	 *            the player
	 * @param team
	 *            the team
	 */
	public void setTeam(Player player, Team team) {
		Team oldTeam = getTeamFromPlayer(player);
		if(oldTeam != null) {
			oldTeam.removePlayer(player);
		}

		if(team == null) {
			playerTeamMap.remove(player);
		} else {
			team.addPlayer(player);
			playerTeamMap.put(player, team);
		}

		Bukkit.getPluginManager().callEvent(new PlayerSelectTeamEvent(player, oldTeam, team));
	}

	/**
	 * Gets the team.
	 *
	 * @param name
	 *            the name
	 * @return the team
	 */
	public Team getTeam(String name) {
		for(Team team : teams) {
			if(team.getName().equals(name)) {
				return team;
			}
		}

		return null;
	}

	/**
	 * Gets the team.
	 *
	 * @param color
	 *            the color
	 * @return the team
	 */
	public Team getTeam(Color color) {
		for(Team team : teams) {
			if(team.getColor() == color) {
				return team;
			}
		}

		return null;
	}

	/**
	 * Gets the teams.
	 *
	 * @return the teams
	 */
	public List<Team> getTeams() {
		return teams;
	}

	/**
	 * Gets the listener.
	 *
	 * @return the listener
	 */
	public TeamListener getListener() {
		return listener;
	}

	/**
	 * Gets the team size.
	 *
	 * @return the team size
	 */
	public int getTeamSize() {
		return teamSize;
	}

	/**
	 * Face to yaw.
	 *
	 * @param blockFace
	 *            the block face
	 * @return the float
	 */
	public static float faceToYaw(BlockFace blockFace) {
		switch(blockFace) {
		case NORTH:
			return -180.0f;
		case EAST:
			return -90.0f;
		case SOUTH:
			return 0.0f;
		case WEST:
			return 90.0f;
		default:
			return 0.0f;
		}
	}

}
