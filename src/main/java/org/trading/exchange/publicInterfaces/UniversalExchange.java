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

	default boolean isAutoMatching() {
		return true;
	}

	Owner getOwner();

	default boolean isOwned(Owner owner) {
		return getOwner().equals(owner);
	}

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
	 * Get all open markets suitable for ExchangeOffer
	 */
	default Collection<? extends Market> getMarkets(ExchangeOffer exchangeOffer) {
		if (exchangeOffer == null) return getMarkets();
		return getMarkets().stream().filter(market -> market.validate(exchangeOffer)).collect(Collectors.toList());
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
	 * Get Market by Id
	 */
	default Market getMarket(String id) {
		return getMarkets().stream().filter(market -> market.getId().equals(id)).findFirst().orElse(null);
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
	 * Validate ExchangeOffer, check if ExchangeOffer can be handled by the Exchange
	 *
	 * @param exchangeOffer
	 * @throws InvalidParameterException - should be thrown when the ExchangeOffer can't be handled by the Exchange
	 */
	ExchangeOffer validate(ExchangeOffer exchangeOffer, Market market) throws InvalidParameterException;

	/**
	 * Accept ExchangeOffer, should invoke {@link #validate(ExchangeOffer, Market)} and accept/process the
	 * ExchangeOffer
	 *
	 * @param exchangeOffer
	 * @throws InvalidParameterException - should be thrown when the ExchangeOffer can't be handled by the Exchange
	 */
	ExchangeOffer accept(ExchangeOffer exchangeOffer, Market market) throws InvalidParameterException;

	/**
	 * Get ExchangeOffers which will match the ExchangeOffer
	 *
	 * @param exchangeOffer
	 * @param market
	 */
	Collection<? extends ExchangeOffer> getMatching(ExchangeOffer exchangeOffer, Market market);

	/**
	 * (Manually) match the ExchangeOffer with matching ExchangeOffers (these should be a result of invocation of
	 * {@link #getMatching(ExchangeOffer, Market)})
	 *
	 * @param exchangeOffer
	 * @param market
	 * @param exchangeOffers
	 * @return Exchanged
	 * @throws InvalidParameterException
	 * @throws IllegalStateException
	 */
	Exchanged match(ExchangeOffer exchangeOffer, Market market, ExchangeOffer... exchangeOffers)
			throws InvalidParameterException, IllegalStateException;
}
