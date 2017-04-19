package com.florianwoelki.survivalgames;

import org.bukkit.plugin.java.JavaPlugin;

import com.florianwoelki.minigameapi.MinigameAPI;
import com.florianwoelki.minigameapi.api.Minigame;
import com.florianwoelki.minigameapi.api.StopReason;
import com.florianwoelki.survivalgames.command.CommandList;
import com.florianwoelki.survivalgames.command.CommandRefill;

public class SurvivalGames extends JavaPlugin implements Minigame {

	@Override
	public void onEnable() {
		MinigameAPI.getInstance().initializeMinigame(this);
		MinigameAPI.getInstance().setMinigameName("SurvivalGames");
		MinigameAPI.getInstance().setChatPrefix("SurvivalGames");
		MinigameAPI.getInstance().setBlockChangesEnabled(true);
		MinigameAPI.getInstance().setAllowSpectatorDeath(true);
		MinigameAPI.getInstance().enableMapVoting();
		MinigameAPI.getInstance().enableEnchantHack();
		MinigameAPI.getInstance().enableDefaultScoreboard();

		MinigameAPI.getInstance().getCommandHandler().register(CommandList.class, new CommandList());
		MinigameAPI.getInstance().getCommandHandler().register(CommandRefill.class, new CommandRefill());
	}

	@Override
	public void startGame() {
	}

	@Override
	public void stopGame(StopReason stopReason) {
	}

}
