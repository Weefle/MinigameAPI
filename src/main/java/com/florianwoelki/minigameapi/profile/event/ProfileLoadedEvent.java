package com.florianwoelki.minigameapi.profile.event;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.florianwoelki.minigameapi.profile.Profile;

public class ProfileLoadedEvent extends Event {

	private static final HandlerList HANDLERS = new HandlerList();

	private Profile profile;

	public ProfileLoadedEvent(Profile profile) {
		this.profile = profile;
	}

	public Profile getProfile() {
		return profile;
	}

	@Override
	public HandlerList getHandlers() {
		return HANDLERS;
	}

	public static HandlerList getHandlerList() {
		return HANDLERS;
	}

}
