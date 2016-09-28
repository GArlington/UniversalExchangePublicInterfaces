package org.trading.exchange.publicInterfaces;

import java.util.UUID;

/**
 * Created by GArlington.
 */
public interface Owner {
	Owner PLATFORM_OWNER = () -> UUID.randomUUID().toString();

	String getId();

	default boolean isOwner(Commodity commodity) {
		return commodity.isOwned(this);
	}

	default boolean isOwner(Exchangeable exchangeable) {
		return exchangeable.isOwned(this);
	}

	default boolean isOwner(Location location) {
		return location.isOwned(this);
	}

	default boolean isOwner(Market market) {
		return market.isOwned(this);
	}

	default boolean isOwner(UniversalExchange platform) {
		return platform.isOwned(this);
	}

	default boolean equals(Owner owner) {
		return getId().equals(owner.getId());
	}
}
