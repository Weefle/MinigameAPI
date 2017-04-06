package com.florianwoelki.minigameapi.team.event;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

import com.florianwoelki.minigameapi.team.Team;

public class PlayerSelectTeamEvent extends PlayerEvent {

	private static HandlerList handlers = new HandlerList();
	private final Team oldTeam;
	private final Team team;

	public PlayerSelectTeamEvent(Player player, Team oldTeam, Team team) {
		super(player);
		this.oldTeam = oldTeam;
		this.team = team;
	}

	public Team getOldTeam() {
		return oldTeam;
	}

	public Team getTeam() {
		return team;
	}

	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}

}
