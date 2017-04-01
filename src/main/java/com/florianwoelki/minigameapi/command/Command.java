package com.florianwoelki.minigameapi.command;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.annotation.RetentionPolicy;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Command {

	String command();

	String usage() default "/<command>";

	String[] alias() default {};

	Sender sender() default Sender.EVERYONE;

	String[] permissions() default {};

	String permissionMessage() default "§cYou do not have enough permission to execute this command!";

}
