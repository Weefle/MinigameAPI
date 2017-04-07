package com.florianwoelki.minigameapi.database.async;

/**
 * The Interface Consumer.
 *
 * @param <T>
 *            the generic type
 */
public interface Consumer<T> {

	/**
	 * Accept.
	 *
	 * @param t
	 *            the t
	 */
	void accept(T t);

}
