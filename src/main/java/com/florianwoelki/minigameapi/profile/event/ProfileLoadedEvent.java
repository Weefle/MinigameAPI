package com.florianwoelki.minigameapi.profile.event;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.florianwoelki.minigameapi.profile.Profile;

/**
 * This event gets called the profile loaded successfully.
 */
public class ProfileLoadedEvent extends Event {

	/** The Constant HANDLERS. */
	private static final HandlerList HANDLERS = new HandlerList();

	/** The profile. */
	private Profile profile;

	/**
	 * Instantiates a new profile loaded event.
	 *
	 * @param profile
	 *            the profile
	 */
	public ProfileLoadedEvent(Profile profile) {
		this.profile = profile;
	}

	/**
	 * Gets the profile.
	 *
	 * @return the profile
	 */
	public Profile getProfile() {
		return profile;
	}

	@Override
	public HandlerList getHandlers() {
		return HANDLERS;
	}

	/**
	 * Gets the handler list.
	 *
	 * @return the handler list
	 */
	public static HandlerList getHandlerList() {
		return HANDLERS;
	}

}
