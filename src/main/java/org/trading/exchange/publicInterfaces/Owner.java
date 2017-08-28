package org.trading.exchange.publicInterfaces;

import org.data.UniversalSerializable;
import org.security.UniquelyIdentifiable;

import java.util.UUID;

/**
 * Created by GArlington.
 */
public interface Owner extends UniversalSerializable, UniquelyIdentifiable {
	Owner PLATFORM_OWNER = () -> UUID.fromString("fdfe1ed5-5346-4dd6-8a65-a79c2e1a84c5").toString();

	default boolean isOwner(Commodity commodity) {
		return this.equals(commodity.getOwner());
	}

	default boolean isOwner(ExchangeOffer exchangeOffer) {
		return this.equals(exchangeOffer.getOwner());
	}

	default boolean isOwner(Location location) {
		return this.equals(location.getOwner());
	}

	default boolean isOwner(Market market) {
		return this.equals(market.getOwner());
	}

	default boolean isOwner(UniversalExchange platform) {
		return this.equals(platform.getOwner());
	}

	default boolean equals(Owner owner) {
		return getId().equals(owner.getId());
	}
}
