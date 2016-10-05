package org.trading.exchange.publicInterfaces;

import org.junit.Before;
import org.junit.Test;

import java.security.InvalidParameterException;
import java.util.Collection;
import java.util.LinkedList;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

/**
 * Created by GArlington.
 */
public class UniversalExchangeTest {
	private UniversalExchange victim;

	@Before
	public void setup() {
		victim = new UniversalExchange() {
			Collection<? extends Market> markets = new LinkedList<>();

			@Override
			public String getName() {
				return getClass().getSimpleName();
			}

			@Override
			public Owner getOwner() {
				return () -> null;
			}

			@Override
			public Collection<? extends Market> getMarkets() {
				return markets;
			}

			@Override
			public Market validate(Market market) throws InvalidParameterException {
				if (getMarkets().contains(market)) {
					throw new InvalidParameterException(market + " already exists on " + this);
				}
				return market;
			}

			@SuppressWarnings("unchecked")
			@Override
			public Market open(Market market) throws IllegalStateException, InvalidParameterException {
				if (!((Collection<Market>) getMarkets()).add(validate(market))) {
					throw new IllegalStateException(market + " can not be created on " + this);
				}
				return market;
			}

			@Override
			public boolean close(Market market) throws IllegalStateException {
				return false;
			}

			@Override
			public ExchangeOffer validate(ExchangeOffer exchangeOffer) throws InvalidParameterException {
				return null;
			}

			@Override
			public ExchangeOffer accept(ExchangeOffer exchangeOffer, Market market) throws InvalidParameterException {
				return null;
			}

			@Override
			public Collection<? extends ExchangeOffer> getMatching(ExchangeOffer exchangeOffer, Market market) {
				return null;
			}

			@Override
			public Exchanged match(ExchangeOffer exchangeOffer, Market market, ExchangeOffer... exchangeOffers)
					throws InvalidParameterException, IllegalStateException {
				return null;
			}
		};
	}

	@Test
	public void getCommodities() throws Exception {
		assertEquals(0, victim.getCommodities().size());

		Market market = mock(Market.class);
		victim.open(market);

		assertEquals(2, victim.getCommodities().size());
	}

	@Test
	public void getCommoditiesByLocation() throws Exception {
		Location location = mock(Location.class);
		Location location2 = mock(Location.class);
		assertEquals(0, victim.getCommodities(location).size());
		assertEquals(0, victim.getCommodities(location2).size());

		Market market = mock(Market.class);
		doReturn(location).when(market).getLocation();

		victim.open(market);
		assertEquals(2, victim.getCommodities(location).size());
		assertEquals(0, victim.getCommodities(location2).size());

		market = mock(Market.class);
		doReturn(location2).when(market).getLocation();

		victim.open(market);
		assertEquals(2, victim.getCommodities(location2).size());
	}

	@Test
	public void getLocations() throws Exception {
		assertEquals(0, victim.getLocations().size());

		Market market = mock(Market.class);
		victim.open(market);

		assertEquals(1, victim.getLocations().size());
	}

	@Test
	public void getLocationsByCommodity() throws Exception {
		Commodity commodity = mock(Commodity.class);
		Location location = mock(Location.class);
		Market market = mock(Market.class);
		doReturn(commodity).when(market).getOffered();
		doReturn(location).when(market).getLocation();
		doReturn(true).when(location).checkCommodity(commodity);
		victim.open(market);

		assertEquals(1, victim.getLocations(commodity).size());
	}

	@Test
	public void getMarkets() throws Exception {
		Market market = mock(Market.class);
		victim.open(market);

		assertEquals(1, victim.getMarkets().size());

		market = mock(Market.class);
		victim.open(market);
		assertEquals(2, victim.getMarkets().size());
	}

	@Test
	public void getMarketById() throws Exception {
		String id = "thisId";
		Market market = mock(Market.class);
		doReturn(id).when(market).getId();

		assertNull(victim.getMarket(id));
		victim.open(market);
		assertNotNull(victim.getMarket(id));
	}

	@Test
	public void getMarketsByExchangeable() throws Exception {
		ExchangeOffer exchangeOffer = mock(ExchangeOffer.class);
		Market market = mock(Market.class);
		doReturn(true).when(market).validate(exchangeOffer);
		victim.open(market);

		assertEquals(1, victim.getMarkets(exchangeOffer).size());
	}

	@Test
	public void getMarketsByLocation() throws Exception {
		Location location = mock(Location.class);
		Market market = mock(Market.class);
		doReturn(location).when(market).getLocation();
		victim.open(market);

		assertEquals(1, victim.getMarkets(location).size());
	}
}