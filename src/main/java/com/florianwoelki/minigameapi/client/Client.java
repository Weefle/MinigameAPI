package com.florianwoelki.minigameapi.client;

import java.util.UUID;

import com.florianwoelki.minigameapi.uuid.UUIDFetcher;

public class Client {

	private String name;
	private UUID uuid;
	
	public Client(UUID uuid) {
		this.uuid = uuid;
		this.name = UUIDFetcher.getName(uuid);
	}
	
	public String getName() {
		return name;
	}
	
	public UUID getUuid() {
		return uuid;
	}
	
}
