package com.florianwoelki.oitc;

import org.bukkit.plugin.java.JavaPlugin;

import com.florianwoelki.minigameapi.MinigameAPI;
import com.florianwoelki.minigameapi.api.Minigame;
import com.florianwoelki.minigameapi.api.StopReason;

public class OITC extends JavaPlugin implements Minigame {

	@Override
	public void onEnable() {
		MinigameAPI.getInstance().initializeMinigame(this);
		MinigameAPI.getInstance().setChatPrefix("OITC");
		MinigameAPI.getInstance().setMinigameName("OITC");
		MinigameAPI.getInstance().setAllowSpectatorDeath(false);
		MinigameAPI.getInstance().setBlockChangesEnabled(false);
	}
	
	@Override
	public void onDisable() {
	}

	@Override
	public void startGame() {
		
	}

	@Override
	public void stopGame(StopReason stopReason) {
	}
	
}
