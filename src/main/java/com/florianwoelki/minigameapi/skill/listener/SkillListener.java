package com.florianwoelki.minigameapi.skill.listener;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import com.florianwoelki.minigameapi.messenger.MessageType;
import com.florianwoelki.minigameapi.messenger.Messenger;
import com.florianwoelki.minigameapi.player.PlayerData;
import com.florianwoelki.minigameapi.player.PlayerWrapper;
import com.florianwoelki.minigameapi.skill.Skill;
import com.florianwoelki.minigameapi.skill.SkillManager;
import com.florianwoelki.minigameapi.skill.event.PlayerUseSkillEvent;

public class SkillListener implements Listener {

	private SkillManager skillManager;

	public SkillListener(SkillManager skillManager) {
		this.skillManager = skillManager;
	}

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		PlayerWrapper playerWrapper = (PlayerWrapper) event.getPlayer();

		if(!playerWrapper.getPlayerMetadata().containsKey("skill")) {
			return;
		}

		PlayerData<?> playerData = playerWrapper.getPlayerMetadata().get("skill");
		Skill skill = (Skill) playerData.getData();

		if(skill == null) {
			return;
		}

		if(!skill.getItemStack().isSimilar(event.getPlayer().getItemInHand())) {
			return;
		}

		if(skillManager.getCooldown().containsKey(playerWrapper.getUniqueId()) && System.currentTimeMillis() < skillManager.getCooldown().get(playerWrapper.getUniqueId())) {
			Messenger.getInstance().message(playerWrapper, MessageType.BAD, "You are still on a cooldown!");
			Bukkit.getPluginManager().callEvent(new PlayerUseSkillEvent(playerWrapper, skill, false));
		} else {
			skill.execute(event);
			Bukkit.getPluginManager().callEvent(new PlayerUseSkillEvent(playerWrapper, skill, true));

			skillManager.getCooldown().put(playerWrapper.getUniqueId(), System.currentTimeMillis() + skill.getCooldown() * 1000);
		}
	}

}
