package com.florianwoelki.minigameapi;

import org.bukkit.entity.Player;
import org.junit.Test;
import org.powermock.api.mockito.PowerMockito;

public class CommandTest {

	@Test
	public void testCommand() {
		Player player = PowerMockito.mock(Player.class);
		PowerMockito.when(player.getName()).thenReturn("Name is working...");
	}

}
