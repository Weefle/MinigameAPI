package com.florianwoelki.minigameapi.profile;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.florianwoelki.minigameapi.Manager;
import com.florianwoelki.minigameapi.MinigameAPI;

/**
 * The Class ProfileManager.
 */
public class ProfileManager extends Manager {

	/** The profiles. */
	private final Map<UUID, Profile> profiles = new HashMap<>();

	@Override
	public void onLoad() {
		for(Player onlinePlayer : Bukkit.getOnlinePlayers()) {
			Profile profile = new Profile(onlinePlayer);
			profiles.put(onlinePlayer.getUniqueId(), profile);
			new ProfileLoader(profile).runTaskAsynchronously(MinigameAPI.getInstance());
		}
	}

	@Override
	public void onUnload() {
		for(Profile profile : profiles.values()) {
			new ProfileSaver(profile).runTaskAsynchronously(MinigameAPI.getInstance());
		}
		profiles.clear();
	}

	@Override
	public void onPlayerJoin(Player player) {
		Profile profile = new Profile(player);
		profiles.put(player.getUniqueId(), profile);
		new ProfileLoader(profile).runTaskAsynchronously(MinigameAPI.getInstance());
	}

	@Override
	public void onPlayerQuit(Player player) {
		Profile profile = getRemovedProfile(player);
		new ProfileSaver(profile).runTaskAsynchronously(MinigameAPI.getInstance());
	}

	/**
	 * Gets the profile.
	 *
	 * @param player
	 *            the player
	 * @return the profile
	 */
	public Profile getProfile(Player player) {
		return profiles.get(player.getUniqueId());
	}

	/**
	 * Gets the removed profile.
	 *
	 * @param player
	 *            the player
	 * @return the removed profile
	 */
	public Profile getRemovedProfile(Player player) {
		return profiles.remove(player.getUniqueId());
	}

}
