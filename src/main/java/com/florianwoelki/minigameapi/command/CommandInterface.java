package com.florianwoelki.minigameapi.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

/**
 * This interface represents a basic command interface.
 * 
 * You can compare this interface with the CommandExecutor.
 */
public interface CommandInterface {

	/**
	 * On command.
	 *
	 * @param commandSender
	 *            the command sender
	 * @param command
	 *            the command
	 * @param cmdLabel
	 *            the cmd label
	 * @param args
	 *            the args
	 * @return true, if successful
	 */
	boolean onCommand(CommandSender commandSender, Command command, String cmdLabel, String[] args);

}
