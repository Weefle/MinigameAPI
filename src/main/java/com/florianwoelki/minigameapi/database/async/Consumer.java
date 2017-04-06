package com.florianwoelki.minigameapi.database.async;

public interface Consumer<T> {

	void accept(T t);

}
