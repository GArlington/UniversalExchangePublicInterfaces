package org.trading.exchange.publicInterfaces;

/**
 * Created by G.Arlington.
 */
public interface UniquelyIdentifiable {
	/**
	 * This must be a unique identifier
	 */
	String getId();


	default int defaultHashCode() {
		return getId() != null ? getId().hashCode() : 0;
	}

	default boolean defaultEquals(UniquelyIdentifiable other) {
		return getId() != null && getId().equals(other.getId());
	}

	int hashCode();

	boolean equals(Object object);
}
