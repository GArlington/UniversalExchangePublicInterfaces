package org.trading.exchange.publicInterfaces;

import java.io.Serializable;
import java.util.Collection;

/**
 * Created by GArlington.
 */
public interface Exchanged extends Serializable {
	default Owner getOwner() {
		return getExchangeOffer() != null ? getExchangeOffer().getOwner() : null;
	}

	default boolean isOwned(Owner owner) {
		return getExchangeOffer() != null && getExchangeOffer().isOwned(owner);
	}

	ExchangeOffer getExchangeOffer();

	Collection<? extends ExchangeOffer> getMatchedExchangeOffers();
}
