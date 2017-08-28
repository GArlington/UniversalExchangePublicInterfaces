package org.trading.exchange.publicInterfaces;

import org.data.UniversalSerializable;
import org.security.UniquelyIdentifiable;

import java.util.Collection;

/**
 * Created by GArlington.
 */
public interface Exchanged extends UniversalSerializable, UniquelyIdentifiable {
	default Owner getOwner() {
		return getExchangeOffer() != null ? getExchangeOffer().getOwner() : null;
	}

	ExchangeOffer getExchangeOffer();

	Collection<? extends ExchangeOffer> getMatchedExchangeOffers();
}
