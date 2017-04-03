package com.florianwoelki.minigameapi.util;

import java.lang.reflect.Field;

import org.bukkit.Color;

public class BukkitUtils {
	
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
