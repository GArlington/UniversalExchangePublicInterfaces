package org.trading.exchange.publicInterfaces;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

/**
 * Created by GArlington.
 */
public class OwnerTest {
	Owner victim;

	@Before
	public void setUp() throws Exception {
		victim = () -> "thisId";
	}

	@After
	public void tearDown() throws Exception {

	}

	@Test
	public void getId() throws Exception {

	}

	@Test
	public void isOwnerCommodity() throws Exception {
		Commodity commodity = mock(Commodity.class);
		doReturn(true).when(commodity).isOwned(victim);

		assertEquals(true, victim.isOwner(commodity));
	}

	@Test
	public void isOwnerExchangeable() throws Exception {
		Exchangeable exchangeable = mock(Exchangeable.class);
		doReturn(true).when(exchangeable).isOwned(victim);

		assertEquals(true, victim.isOwner(exchangeable));
	}

	@Test
	public void isOwnerLocation() throws Exception {
		Location location = mock(Location.class);
		doReturn(true).when(location).isOwned(victim);

		assertEquals(true, victim.isOwner(location));
	}

	@Test
	public void isOwnerMarket() throws Exception {
		Market market = mock(Market.class);
		doReturn(true).when(market).isOwned(victim);

		assertEquals(true, victim.isOwner(market));
	}

	@Test
	public void isOwnerUniversalExchange() throws Exception {
		UniversalExchange exchange = mock(UniversalExchange.class);
		doReturn(true).when(exchange).isOwned(victim);

		assertEquals(true, victim.isOwner(exchange));
	}

}