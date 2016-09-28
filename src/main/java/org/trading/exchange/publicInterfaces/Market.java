package org.trading.exchange.publicInterfaces;

import java.io.Serializable;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Created by GArlington.
 */
public interface Market extends Serializable {

	String getId();

	String getName();

	Location getLocation();

	Commodity getOffered();

	Commodity getRequired();

	Collection<? extends Exchangeable> getOrders();

	Owner getOwner();

	default boolean isOwned(Owner owner) {
		return getOwner().equals(owner);
	}

	default boolean isAutoMatching() {
		return true;
	}

	default Collection<? extends Exchangeable> getOrders(Exchangeable.State state) {
		return getOrders().stream()
				.filter(order -> (state.equals(order.getExchangeableState())))
				.sorted()
				.collect(Collectors.toList());
	}

	boolean accept(Exchangeable exchangeable);

	default boolean validate() throws IllegalStateException {
		if (!validateLocation(getLocation())) {
			throw new IllegalStateException(this + " configuration is invalid. "
//                    + getLocation() + " can't handle offered:" + getOffered() + " or required:" + getRequired()
			);
		}
		if (!getOrders().stream().allMatch(this::validate)) {
			throw new IllegalStateException(this + " configuration is invalid. "
//                    + getOrders() + " don't match offered:" + getOffered() + " or required:" + getRequired()
			);
		}
		return true;
	}

	default boolean validateLocation(Location location) {
		return location.checkCommodity(getOffered()) && location.checkCommodity(getRequired());
	}

	default boolean validate(Exchangeable exchangeable) {
		return isMarket(exchangeable) || isCounter(exchangeable);
	}

	default boolean isMarket(Exchangeable exchangeable) {
		return getOffered().equals(exchangeable.getOffered()) && getRequired().equals(exchangeable.getRequired());
	}

	default boolean isCounter(Exchangeable exchangeable) {
		return getOffered().equals(exchangeable.getRequired()) && getRequired().equals(exchangeable.getOffered());
	}

	interface Builder<T> extends org.processing.Builder {
		Builder<T> setId(String id);

		Builder<T> setName(String name);

		Builder<T> setLocation(Location location);

		Builder<T> setOffered(Commodity offered);

		Builder<T> setRequired(Commodity required);

		Builder<T> accept(Exchangeable exchangeable);

		Builder<T> setOwner(Owner owner);

		Builder<T> setAutoMatching(boolean autoMatching);
	}
}
