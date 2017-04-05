package com.florianwoelki.minigameapi.command;

import java.lang.reflect.Method;
import java.util.Arrays;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.PluginIdentifiableCommand;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class DynamicCommand extends org.bukkit.command.Command implements PluginIdentifiableCommand {

	private String[] permissions;
	private String permissionMessage;

	private Sender sender;
	private CommandExecutor commandExecutor;
	private Method owner;

	private JavaPlugin javaPlugin;

	protected DynamicCommand(JavaPlugin javaPlugin, CommandExecutor commandExecutor, String name, String description, Method owner, String[] aliases, String usage, String[] permissions, String permissionMessage, Sender sender) {
		super(name, "Description: " + name, usage, Arrays.asList(aliases));
		this.javaPlugin = javaPlugin;
		this.commandExecutor = commandExecutor;
		this.owner = owner;
		this.permissions = permissions;
		this.permissionMessage = permissionMessage;
		this.sender = sender;
	}

	@Override
	public Plugin getPlugin() {
		return javaPlugin;
	}

	@Override
	public boolean execute(CommandSender commandSender, String commandLabel, String[] args) {
		try {
			if(!isValidExecutor(commandSender)) {
				commandSender.sendMessage("§cYou must be a " + sender.getSenderClass().getSimpleName() + " to execute this command.");
				return false;
			}

			if(permissions.length != 0 && !hasAnyPermission(commandSender)) {
				commandSender.sendMessage(permissionMessage);
				return false;
			}

			return (boolean) owner.invoke(commandExecutor, commandSender, this, commandLabel, args);
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	private boolean hasAnyPermission(CommandSender commandSender) {
		boolean hasPermission = false;
		for(String permission : permissions) {
			if(commandSender.hasPermission(permission) || commandSender.isOp() || commandSender instanceof ConsoleCommandSender) {
				hasPermission = true;
			}
		}
		return hasPermission;
	}

	private boolean isValidExecutor(CommandSender commandSender) {
		return (commandSender instanceof Player && sender == Sender.PLAYER) || (commandSender instanceof ConsoleCommandSender && sender == Sender.CONSOLE) || sender == Sender.EVERYONE;
	}

}
