package org.trading.exchange.publicInterfaces;

import java.io.Serializable;
import java.util.Collection;

/**
 * Created by GArlington.
 */
public interface Exchanged extends Serializable, UniquelyIdentifiable {
	default Owner getOwner() {
		return getExchangeOffer() != null ? getExchangeOffer().getOwner() : null;
	}

	default boolean isOwnedBy(Owner owner) {
		return getExchangeOffer() != null && getExchangeOffer().isOwnedBy(owner);
	}

	ExchangeOffer getExchangeOffer();

	Collection<? extends ExchangeOffer> getMatchedExchangeOffers();
}
