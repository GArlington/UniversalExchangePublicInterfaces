package org.trading.exchange.publicInterfaces;

import java.io.Serializable;
import java.util.Collection;

/**
 * Created by GArlington.
 */
public interface Exchanged extends Serializable {
	default Owner getOwner() {
		return getExchangeable() != null ? getExchangeable().getOwner() : null;
	}

	default boolean isOwned(Owner owner) {
		return getExchangeable() != null && getExchangeable().isOwned(owner);
	}

	Exchangeable getExchangeable();

	Collection<? extends Exchangeable> getMatchedExchangeables();
}
