package com.florianwoelki.minigameapi.vote;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

import com.florianwoelki.minigameapi.Manager;
import com.florianwoelki.minigameapi.MinigameAPI;
import com.florianwoelki.minigameapi.game.GameState;
import com.florianwoelki.minigameapi.messenger.Messenger;
import com.florianwoelki.minigameapi.vote.event.PlayerVoteForMapEvent;
import com.florianwoelki.minigameapi.vote.event.VotingEndEvent;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

public class VoteManager extends Manager {

	private final List<VoteMap> maps = new ArrayList<>();
	private final Map<Player, VoteMap> votes = new HashMap<>();

	private VoteListener voteListener;
	private VoteMap votedMap;

	private boolean isVotingEnabled = true;

	@Override
	public void onLoad() {
		loadMaps();

		voteListener = new VoteListener(this);
		Bukkit.getPluginManager().registerEvents(voteListener, MinigameAPI.getInstance());
	}

	@Override
	public void onUnload() {
		HandlerList.unregisterAll(voteListener);
	}

	@Override
	public void onUpdateTimer(int newTime) {
		if(newTime % 15 == 0) {
			for(Player onlinePlayer : Bukkit.getOnlinePlayers()) {
				sendVoting(onlinePlayer);
			}
		}

		if(isVotingEnabled() && newTime <= 10) {
			finishVoting();
		}
	}

	@Override
	public void onStartGame() {
		if(votedMap == null) {
			finishVoting();
		}

		votedMap.setWorld(MinigameAPI.getInstance().requestMap(votedMap.getWorldName()));
		votedMap.getWorld().setAutoSave(false);
	}

	public void sendVoting(Player player) {
		player.sendMessage("§m--------- §6§l Map Vote §7§m----------");
		player.sendMessage("§r");

		for(VoteMap voteMap : maps) {
			TextComponent tc = new TextComponent();
			tc.setText("§b" + voteMap.getDisplayName() + " §8» §fClick to vote");
			tc.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/vm " + voteMap.getDisplayName()));
			tc.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§aClick to vote for the map §b" + voteMap.getDisplayName() + "§a.").create()));

			player.spigot().sendMessage(tc);
		}

		player.sendMessage("§r");
		player.sendMessage("§m--------- §6§l Map Vote §7§m----------");
		player.playSound(player.getLocation(), Sound.ORB_PICKUP, 1f, 1f);
	}

	public void finishVoting() {
		if(MinigameAPI.getInstance().getGame().getGameState() != GameState.LOBBY) {
			return;
		}

		isVotingEnabled = false;

		votedMap = getMapWithMostVotes();
		for(Player onlinePlayer : Bukkit.getOnlinePlayers()) {
			onlinePlayer.playSound(onlinePlayer.getLocation(), Sound.ANVIL_LAND, 1f, 1f);
		}

		Bukkit.broadcastMessage(" ");
		Messenger.getInstance().spaceBroadcast("§c§oVoting ended!");
		Messenger.getInstance().spaceBroadcast("§6Map: §3" + votedMap.getDisplayName());
		Bukkit.broadcastMessage(" ");

		Bukkit.getPluginManager().callEvent(new VotingEndEvent(votedMap));
	}

	public void voteMap(Player player, VoteMap voteMap) {
		votes.put(player, voteMap);
		Bukkit.getPluginManager().callEvent(new PlayerVoteForMapEvent(player, voteMap));
	}

	public VoteMap getMapWithMostVotes() {
		List<VoteMap> availableMaps = new ArrayList<>();
		availableMaps.addAll(maps);
		Collections.shuffle(availableMaps);

		VoteMap bestMap = maps.get(0);
		int bestVotes = 0;
		for(VoteMap map : availableMaps) {
			int votes = getVotes(map);
			if(votes > bestVotes) {
				bestMap = map;
				bestVotes = votes;
			}
		}
		return bestMap;
	}

	public int getVotes(VoteMap voteMap) {
		int votes = 0;
		for(VoteMap map : this.votes.values()) {
			if(map == voteMap) {
				votes++;
			}
		}
		return votes;
	}

	private void loadMaps() {
		maps.clear();

		List<VoteMap> allMaps = new ArrayList<>();
		int mapsCount = 0;

		YamlConfiguration config = YamlConfiguration.loadConfiguration(new File(MinigameAPI.getInstance().getDataFolder(), "maps.yml"));
		for(String mapName : config.getKeys(false)) {
			ConfigurationSection section = config.getConfigurationSection(mapName);
			String displayName = section.getString("DisplayName");
			int priority = section.getInt("Priority");

			VoteMap voteMap = new VoteMap(displayName, mapName);
			for(int i = 0; i < priority; i++) {
				allMaps.add(voteMap);
			}
			mapsCount++;
		}

		int size = 0;
		while(size != Math.min(3, mapsCount)) {
			int randomId = (int) (Math.random() * allMaps.size());

			if(randomId < allMaps.size()) {
				VoteMap voteMap = allMaps.get(randomId);

				System.out.println("[MinigameAPI] " + voteMap.getDisplayName() + " added!"); // TODO: Add custom logger.
				maps.add(voteMap);

				List<VoteMap> allMapsClone = new ArrayList<>();
				allMapsClone.addAll(allMaps);
				for(VoteMap vmap : allMapsClone) {
					if(vmap.getWorldName().equalsIgnoreCase(voteMap.getWorldName())) {
						allMaps.remove(vmap);
					}
				}
				size++;
			}
		}
	}

	public VoteMap getMapByName(String mapName) {
		for(VoteMap voteMap : maps) {
			if(voteMap.getDisplayName().equalsIgnoreCase(mapName)) {
				return voteMap;
			}
		}
		return null;
	}

	public boolean hasVoted(Player player) {
		return votes.containsKey(player);
	}

	public void removeVote(Player player) {
		votes.remove(player);
	}

	public VoteListener getVoteListener() {
		return voteListener;
	}

	public VoteMap getVotedMap() {
		return votedMap;
	}

	public Map<Player, VoteMap> getVotes() {
		return votes;
	}

	public boolean isVotingEnabled() {
		return isVotingEnabled;
	}

}
