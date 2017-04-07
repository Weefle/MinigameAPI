package com.florianwoelki.minigameapi.statistic;

/**
 * The Class StatisticObject.
 *
 * @param <V>
 *            the value type
 */
public class StatisticObject<V> {

	/** The name. */
	private String name;

	/** The value. */
	private V value;

	/**
	 * Instantiates a new statistic object.
	 *
	 * @param name
	 *            the name
	 * @param value
	 *            the value
	 */
	public StatisticObject(String name, V value) {
		this.name = name;
		this.value = value;
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
	 * Gets the value.
	 *
	 * @return the value
	 */
	public V getValue() {
		return value;
	}

}
