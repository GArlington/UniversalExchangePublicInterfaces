package org.trading.exchange.publicInterfaces;

import java.io.Serializable;
import java.security.InvalidParameterException;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by GArlington.
 */
public interface UniversalExchange extends Serializable {
	String getName();

	/**
	 * Get all commodities currently handled by the Exchange
	 */
	default Collection<? extends Commodity> getCommodities() {
		return getCommodities(null);
	}

	/**
	 * Get all commodities currently handled by the Exchange at Location
	 */
	default Collection<? extends Commodity> getCommodities(Location location) {
		return Stream.concat(getMarkets(location).stream().map(Market::getOffered),
				getMarkets(location).stream().map(Market::getRequired)).collect(Collectors.toList());
	}

	/**
	 * Get all Locations that this Exchange can handle
	 */
	default Collection<? extends Location> getLocations() {
		return getLocations(null);
	}

	/**
	 * Get Locations that can handle a Commodity at this Exchange
	 *
	 * @param commodity
	 */
	default Collection<? extends Location> getLocations(Commodity commodity) {
		Stream<? extends Location> stream = getMarkets().stream().map(Market::getLocation);
		if (commodity != null) {
			stream = stream.filter(location -> location.checkCommodity(commodity));
		}
		return stream.collect(Collectors.toList());
	}

	/**
	 * Get all open markets
	 */
	Collection<? extends Market> getMarkets();

	/**
	 * Get all open markets suitable for Exchangeable
	 */
	default Collection<? extends Market> getMarkets(Exchangeable exchangeable) {
		if (exchangeable == null) return getMarkets();
		return getMarkets().stream().filter(market -> market.validate(exchangeable)).collect(Collectors.toList());
	}

	/**
	 * Get all open markets at a Location
	 */
	default Collection<? extends Market> getMarkets(Location location) {
		if (location == null) return getMarkets();
		return getMarkets().stream().filter(market -> market.getLocation().equals(location))
				.collect(Collectors.toList());
	}

	/**
	 * Validate Market, check if Market can be handled by the Exchange
	 *
	 * @param market
	 * @throws InvalidParameterException - should be thrown when the Market can't be handled by the Exchange
	 */
	Market validate(Market market) throws InvalidParameterException;

	/**
	 * Open new market, should invoke {@link #validate(Market)}
	 *
	 * @param market
	 * @throws IllegalStateException     - should be thrown when the Exchange can't accept/handle the market
	 * @throws InvalidParameterException - should be thrown when the market is invalid
	 */
	Market open(Market market) throws IllegalStateException, InvalidParameterException;

	/**
	 * Close a market
	 *
	 * @param market
	 * @throws IllegalStateException - should be thrown if the market can't be closed
	 */
	boolean close(Market market) throws IllegalStateException;

	/**
	 * Validate Exchangeable, check if Exchangeable can be handled by the Exchange
	 *
	 * @param exchangeable
	 * @throws InvalidParameterException - should be thrown when the Exchangeable can't be handled by the Exchange
	 */
	Exchangeable validate(Exchangeable exchangeable) throws InvalidParameterException;

	/**
	 * Accept Exchangeable, should invoke {@link #validate(Exchangeable)} and accept/process the Exchangeable
	 *
	 * @param exchangeable
	 * @throws InvalidParameterException - should be thrown when the Exchangeable can't be handled by the Exchange
	 */
	Exchangeable accept(Exchangeable exchangeable) throws InvalidParameterException;

	/**
	 * Get Exchangeables which will match the Exchangeable
	 *
	 * @param exchangeable
	 */
	Collection<? extends Exchangeable> getMatching(Exchangeable exchangeable);

	/**
	 * (Manually) match the Exchangeable with matching Exchangeables (these should be a result of invocation of
	 * {@link #getMatching(Exchangeable)})
	 *
	 * @param exchangeable
	 * @param exchangeables
	 * @return Exchanged
	 * @throws InvalidParameterException
	 * @throws IllegalStateException
	 */
	Exchanged match(Exchangeable exchangeable, Collection<? extends Exchangeable> exchangeables)
			throws InvalidParameterException, IllegalStateException;
}
