package org.trading.exchange.publicInterfaces;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

/**
 * Created by GArlington.
 */
public class OwnerTest {
	private String id = UUID.randomUUID().toString();
	private Owner victim;

	@Before
	public void setUp() throws Exception {
		victim = () -> id;
	}

	@After
	public void tearDown() throws Exception {

	}

	@Test
	public void getId() throws Exception {
		assertEquals(id, victim.getId());
	}

	@Test
	public void isOwnerCommodity() throws Exception {
		Commodity commodity = mock(Commodity.class);
		doReturn(true).when(commodity).isOwnedBy(victim);

		assertEquals(true, victim.isOwner(commodity));
	}

	@Test
	public void isOwnerExchangeOffer() throws Exception {
		ExchangeOffer exchangeOffer = mock(ExchangeOffer.class);
		doReturn(true).when(exchangeOffer).isOwnedBy(victim);

		assertEquals(true, victim.isOwner(exchangeOffer));
	}

	@Test
	public void isOwnerLocation() throws Exception {
		Location location = mock(Location.class);
		doReturn(true).when(location).isOwnedBy(victim);

		assertEquals(true, victim.isOwner(location));
	}

	@Test
	public void isOwnerMarket() throws Exception {
		Market market = mock(Market.class);
		doReturn(true).when(market).isOwnedBy(victim);

		assertEquals(true, victim.isOwner(market));
	}

	@Test
	public void isOwnerUniversalExchange() throws Exception {
		UniversalExchange exchange = mock(UniversalExchange.class);
		doReturn(true).when(exchange).isOwnedBy(victim);

		assertEquals(true, victim.isOwner(exchange));
	}

	@Test
	public void equals() throws Exception {
		assertEquals(true, victim.equals(victim));

		Owner test = mock(Owner.class);
		assertEquals(false, victim.equals(test));
	}
}