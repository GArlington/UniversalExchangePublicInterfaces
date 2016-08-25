package org.trading.exchange.publicInterfaces;

import java.io.Serializable;
import java.util.Collection;

/**
 * Created by GArlington.
 */
public interface UniversalExchange extends Serializable {
	String getName();

	Collection<Location> getLocations();

	Collection<Market> getMarkets();

	Market openMarket(Market market);

	Market closeMarket(Market market);

	Exchangeable validateOrder(Exchangeable exchangeable) throws IllegalStateException;

	Exchangeable acceptOrder(Exchangeable exchangeable) throws IllegalStateException;

	Collection<Exchangeable> getMatchingOrders(Exchangeable exchangeable);

	Exchanged matchOrder(Exchangeable exchangeable, Collection<Exchangeable> exchangeables);
}
