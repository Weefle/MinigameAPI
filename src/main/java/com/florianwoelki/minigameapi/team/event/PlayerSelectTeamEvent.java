package com.florianwoelki.minigameapi.team.event;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

import com.florianwoelki.minigameapi.team.Team;

/**
 * This event gets called when a player selects a team.
 */
public class PlayerSelectTeamEvent extends PlayerEvent {

	/** The handlers. */
	private static HandlerList handlers = new HandlerList();

	/** The old team. */
	private final Team oldTeam;

	/** The team. */
	private final Team team;

	/**
	 * Instantiates a new player select team event.
	 *
	 * @param player
	 *            the player
	 * @param oldTeam
	 *            the old team
	 * @param team
	 *            the team
	 */
	public PlayerSelectTeamEvent(Player player, Team oldTeam, Team team) {
		super(player);
		this.oldTeam = oldTeam;
		this.team = team;
	}

	/**
	 * Gets the old team.
	 *
	 * @return the old team
	 */
	public Team getOldTeam() {
		return oldTeam;
	}

	/**
	 * Gets the team.
	 *
	 * @return the team
	 */
	public Team getTeam() {
		return team;
	}

	public HandlerList getHandlers() {
		return handlers;
	}

	/**
	 * Gets the handler list.
	 *
	 * @return the handler list
	 */
	public static HandlerList getHandlerList() {
		return handlers;
	}

}
