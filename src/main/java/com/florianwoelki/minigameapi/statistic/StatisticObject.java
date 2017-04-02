package com.florianwoelki.minigameapi.statistic;

public class StatisticObject<V> {

	private String name;
	private V value;

	public StatisticObject(String name, V value) {
		this.name = name;
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public V getValue() {
		return value;
	}

}
