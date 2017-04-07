package com.florianwoelki.minigameapi.util;

import java.lang.reflect.Field;

import org.bukkit.Color;

/**
 * This class represents some basic Bukkit Utils.
 */
public class BukkitUtils {

	/**
	 * With this method you can return a color by their name
	 * 
	 * @param name
	 *            String for the color
	 * @return Color specified with the name
	 */
	public static Color getColorFromName(String name) {
		try {
			Field field = Color.class.getDeclaredField(name);
			field.setAccessible(true);
			return (Color) field.get(null);
		} catch(Exception e) {
			e.printStackTrace();
		}

		return null;
	}

}
