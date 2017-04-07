package com.florianwoelki.minigameapi.kit;

/**
 * The Class Kit.
 */
public abstract class Kit implements KitAction {

	/** The name. */
	protected String name;

	/**
	 * Instantiates a new kit.
	 *
	 * @param name
	 *            the name
	 */
	public Kit(String name) {
		this.name = name;
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

}
