package com.florianwoelki.minigameapi.skill.listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.metadata.MetadataValue;

import com.florianwoelki.minigameapi.messenger.MessageType;
import com.florianwoelki.minigameapi.messenger.Messenger;
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
		Player player = event.getPlayer();

		if(!player.hasMetadata("skill")) {
			return;
		}

		MetadataValue metadataValue = player.getMetadata("skill").get(0);
		Skill skill = skillManager.getSkillByName(metadataValue.asString());

		if(skill == null) {
			return;
		}

		if(!skill.getItemStack().isSimilar(event.getPlayer().getItemInHand())) {
			return;
		}

		if(skillManager.getCooldown().containsKey(player.getUniqueId()) && System.currentTimeMillis() < skillManager.getCooldown().get(player.getUniqueId())) {
			Messenger.getInstance().message(player, MessageType.BAD, "You are still on a cooldown!");
			Bukkit.getPluginManager().callEvent(new PlayerUseSkillEvent(player, skill, false));
		} else {
			skill.execute(event);
			Bukkit.getPluginManager().callEvent(new PlayerUseSkillEvent(player, skill, true));

			skillManager.getCooldown().put(player.getUniqueId(), System.currentTimeMillis() + skill.getCooldown() * 1000);
		}
	}

}
