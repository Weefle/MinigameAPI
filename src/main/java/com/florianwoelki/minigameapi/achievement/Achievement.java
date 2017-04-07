package com.florianwoelki.minigameapi.achievement;

/**
 * The Class Achievement.
 */
public class Achievement {

	private int id;
	private String name;
	private String description;

	/**
	 * Instantiates a new achievement.
	 *
	 * @param id
	 *            the id for the achievement
	 * @param name
	 *            the name for the achievement
	 * @param description
	 *            the description for the achievement
	 */
	public Achievement(int id, String name, String description) {
		this.id = id;
		this.name = name;
		this.description = description;
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Gets the description.
	 *
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

}
