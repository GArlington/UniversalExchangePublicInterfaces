package org.trading.exchange.publicInterfaces;

import org.junit.Before;
import org.junit.Test;

import java.security.InvalidParameterException;
import java.util.Collection;
import java.util.LinkedList;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

/**
 * Created by GArlington.
 */
public class UniversalExchangeTest {
	UniversalExchange victim;

	@Before
	public void setup() {
		victim = new UniversalExchange() {
			Collection<? extends Market> markets = new LinkedList<>();

			@Override
			public String getName() {
				return getClass().getSimpleName();
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
			public Exchangeable validate(Exchangeable exchangeable) throws InvalidParameterException {
				return null;
			}

			@Override
			public Exchangeable accept(Exchangeable exchangeable) throws InvalidParameterException {
				return null;
			}

			@Override
			public Collection<? extends Exchangeable> getMatching(Exchangeable exchangeable) {
				return null;
			}

			@Override
			public Exchanged match(Exchangeable exchangeable, Collection<? extends Exchangeable> exchangeables)
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
	public void getMarketsByExchangeable() throws Exception {
		Exchangeable exchangeable = mock(Exchangeable.class);
		Market market = mock(Market.class);
		doReturn(true).when(market).validate(exchangeable);
		victim.open(market);

		assertEquals(1, victim.getMarkets(exchangeable).size());
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