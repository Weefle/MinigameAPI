package com.florianwoelki.minigameapi.kit;

public abstract class Kit implements KitAction {

	protected String name;

	public Kit(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

}
