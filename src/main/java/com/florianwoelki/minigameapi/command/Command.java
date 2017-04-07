package com.florianwoelki.minigameapi.command;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.annotation.RetentionPolicy;

/**
 * This annotation represents a command.
 * 
 * This is for every command you can register with the CommandHandler.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Command {

	/**
	 * Command.
	 *
	 * @return the string
	 */
	String command();

	/**
	 * Usage.
	 *
	 * @return the string
	 */
	String usage() default "/<command>";

	/**
	 * Alias.
	 *
	 * @return the string[]
	 */
	String[] alias() default {};

	/**
	 * Sender.
	 *
	 * @return the sender
	 */
	Sender sender() default Sender.EVERYONE;

	/**
	 * Permissions.
	 *
	 * @return the string[]
	 */
	String[] permissions() default {};

	/**
	 * Permission message.
	 *
	 * @return the string
	 */
	String permissionMessage() default "§cYou do not have enough permission to execute this command!";

}
