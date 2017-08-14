package org.trading.exchange.publicInterfaces;

import org.util.StringUtilities;

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

	default String defaultToString() {
		return StringUtilities.toString(this);
	}

	default String simpleToString() {
		return getClass().getName() + "{" + "id='" + getId() + "'}";
	}
}
