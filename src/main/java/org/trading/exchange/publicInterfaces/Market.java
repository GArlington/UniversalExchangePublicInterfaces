package org.trading.exchange.publicInterfaces;

import java.io.Serializable;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Created by GArlington.
 */
public interface Market extends Serializable, UniquelyIdentifiable {
	String getName();

	Location getLocation();

	Commodity getOffered();

	Commodity getRequired();

	Collection<? extends ExchangeOffer> getOffers();

	Owner getOwner();

	default boolean isOwnedBy(Owner owner) {
		return getOwner().equals(owner);
	}

	default boolean isFair() {
		return isAutoMatching();
	}

	default boolean isAutoMatching() {
		return true;
	}

	default Collection<? extends ExchangeOffer> getOffers(ExchangeOffer.State state) {
		if (state == null) return getOffers();
		return getOffers().stream()
				.filter(order -> (state.equals(order.getState())))
				.sorted()
				.collect(Collectors.toList());
	}

	default ExchangeOffer accept(ExchangeOffer exchangeOffer) {
		if (validate(exchangeOffer)) {
			synchronized (getOffers()) {
				@SuppressWarnings("unchecked")
				Collection<ExchangeOffer> exchangeOffers =
						(Collection<ExchangeOffer>) getOffers();
				if (exchangeOffers.add(exchangeOffer)) {
					return exchangeOffer;
				}
			}
		}
		return null;
	}

	default boolean validate() throws IllegalStateException {
		if (!validate(getLocation())) {
			throw new IllegalStateException(this + " configuration is invalid. "
//                    + getLocation() + " can't handle offered:" + getOffered() + " or required:" + getRequired()
			);
		}
		if (!getOffers().stream().allMatch(this::validate)) {
			throw new IllegalStateException(this + " configuration is invalid. "
//                    + getOffers() + " don't match offered:" + getOffered() + " or required:" + getRequired()
			);
		}
		return true;
	}

	default boolean validate(Location location) {
		return location.checkCommodity(getOffered()) && location.checkCommodity(getRequired());
	}

	default boolean validate(ExchangeOffer exchangeOffer) {
		return isMarket(exchangeOffer) || isCounter(exchangeOffer);
	}

	default boolean isMarket(ExchangeOffer exchangeOffer) {
		return getOffered().equals(exchangeOffer.getOffered()) && getRequired().equals(exchangeOffer.getRequired());
	}

	default boolean isCounter(ExchangeOffer exchangeOffer) {
		return getOffered().equals(exchangeOffer.getRequired()) && getRequired().equals(exchangeOffer.getOffered());
	}

	default boolean equals(Market market) {
		return getId().equals(market.getId());
	}

	interface Builder<T> extends org.processing.Builder {
		Builder<T> setId(String id);

		Builder<T> setName(String name);

		Builder<T> setLocation(Location location);

		Builder<T> setOffered(Commodity offered);

		Builder<T> setRequired(Commodity required);

		Builder<T> accept(ExchangeOffer exchangeOffer);

		Builder<T> setOwner(Owner owner);

		Builder<T> setAutoMatching(boolean autoMatching);
	}
}
