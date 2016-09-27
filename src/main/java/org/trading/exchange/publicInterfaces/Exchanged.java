package org.trading.exchange.publicInterfaces;

import java.io.Serializable;
import java.util.Collection;

/**
 * Created by GArlington.
 */
public interface Exchanged extends Serializable {
	Owner getOwner();

	default boolean isOwned(Owner owner) {
		return getExchangeable().isOwned(owner);
	}

	Exchangeable getExchangeable();

	Collection<? extends Exchangeable> getMatchedExchangeables();
}
