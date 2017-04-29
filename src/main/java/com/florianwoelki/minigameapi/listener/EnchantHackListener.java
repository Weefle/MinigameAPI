package com.florianwoelki.minigameapi.listener;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.EnchantingInventory;
import org.bukkit.inventory.ItemStack;

import com.florianwoelki.minigameapi.MinigameAPI;

/**
 * The listener interface for receiving enchantHack events.
 * The class that is interested in processing a enchantHack
 * event implements this interface, and the object created
 * with that class is registered with a component using the
 * component's <code>addEnchantHackListener<code> method. When
 * the enchantHack event occurs, that object's appropriate
 * method is invoked.
 *
 * @see EnchantHackEvent
 */
public class EnchantHackListener implements Listener {

	/**
	 * On inventory open.
	 *
	 * @param event the event
	 */
	@EventHandler
	public void onInventoryOpen(InventoryOpenEvent event) {
		if(event.isCancelled() || !MinigameAPI.getInstance().getGame().isGameStarted()) {
			return;
		}

		if(event.getInventory().getType() != InventoryType.ENCHANTING) {
			return;
		}

		EnchantingInventory inventory = (EnchantingInventory) event.getInventory();
		inventory.setSecondary(new ItemStack(Material.INK_SACK, 64, (short) 4));
		if(event.getPlayer() instanceof Player) {
			resetEnchantSeed((Player) event.getPlayer());
		}
	}

	/**
	 * On inventory click.
	 *
	 * @param event the event
	 */
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		if(event.isCancelled() || !MinigameAPI.getInstance().getGame().isGameStarted()) {
			return;
		}

		if(event.getInventory().getType() != InventoryType.ENCHANTING) {
			return;
		}

		if(event.getSlot() == 1) {
			event.setCancelled(true);
		} else if(event.getSlot() == 0 && event.getWhoClicked() instanceof Player) {
			resetEnchantSeed((Player) event.getWhoClicked());
		}
	}

	/**
	 * On inventory close.
	 *
	 * @param event the event
	 */
	@EventHandler
	public void onInventoryClose(InventoryCloseEvent event) {
		if(!MinigameAPI.getInstance().getGame().isGameStarted()) {
			return;
		}

		if(event.getInventory().getType() != InventoryType.ENCHANTING) {
			return;
		}

		EnchantingInventory inventory = (EnchantingInventory) event.getInventory();
		inventory.setSecondary(null);
		if(event.getPlayer() instanceof Player) {
			resetEnchantSeed((Player) event.getPlayer());
		}
	}

	/**
	 * Reset enchant seed.
	 *
	 * @param player the player
	 */
	private void resetEnchantSeed(Player player) {
		try {
			String version = org.bukkit.Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];
			Class<?> craftPlayerClass = Class.forName("org.bukkit.craftbukkit." + version + ".entity.CraftPlayer");
			Class<?> entityHumanClass = Class.forName("net.minecraft.server." + version + ".EntityHuman");
			Class<?> craftInventoryClass = Class.forName("org.bukkit.craftbukkit." + version + ".inventory.CraftInventory");
			Class<?> containerEnchantTableInventoryClass = Class.forName("net.minecraft.server." + version + ".ContainerEnchantTableInventory");
			Class<?> containerEnchantTableClass = Class.forName("net.minecraft.server." + version + ".ContainerEnchantTable");

			Method method = craftPlayerClass.getDeclaredMethod("getHandle", new Class[0]);
			method.setAccessible(true);
			Object entityHuman = method.invoke(player, new Object[0]);

			method = entityHumanClass.getDeclaredMethod("b", new Class[] { Integer.TYPE });
			method.setAccessible(true);
			method.invoke(entityHuman, new Object[] { Integer.valueOf(0) });

			method = entityHumanClass.getDeclaredMethod("ci", new Class[0]);
			method.setAccessible(true);
			int newSeed = ((Integer) method.invoke(entityHuman, new Object[0])).intValue();
			if(player.getOpenInventory() != null && player.getOpenInventory().getType() == InventoryType.ENCHANTING) {
				method = craftInventoryClass.getDeclaredMethod("getInventory", new Class[0]);
				method.setAccessible(true);
				Object containerEnchantTableInventory = method.invoke(player.getOpenInventory().getTopInventory(), new Object[0]);

				Field field = containerEnchantTableInventoryClass.getDeclaredField("enchantTable");
				field.setAccessible(true);
				Object containerEnchantTable = field.get(containerEnchantTableInventory);

				field = containerEnchantTableClass.getDeclaredField("f");
				field.setAccessible(true);
				field.set(containerEnchantTable, Integer.valueOf(newSeed));
			}
		} catch(Exception e) {
			System.out.println("[MinigameAPI] Can't reset enchant seed!");
			e.printStackTrace();
		}
	}

}
