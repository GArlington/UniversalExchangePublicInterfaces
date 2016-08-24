package org.trading.exchange.publicInterfaces;

import java.io.Serializable;
import java.util.Collection;

/**
 * Created by GArlington.
 */
public interface Market extends Serializable {
	String getId();

	Location getLocation();

	String getName();

	Commodity getOffered();

	Commodity getRequired();

	Collection<Exchangeable> getOrders();

	Collection<Exchangeable> getOrders(Exchangeable.State state);

	void validateMarket();

	boolean validateLocation();
}
