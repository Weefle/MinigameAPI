package com.florianwoelki.minigameapi.player;

public class PlayerData<T> {

	private T t;

	public PlayerData(T t) {
		this.t = t;
	}

	public T getData() {
		return t;
	}

}
