package org.trading.exchange.publicInterfaces;

import org.junit.Before;
import org.junit.Test;

import java.util.Collection;
import java.util.LinkedList;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

/**
 * Created by GArlington.
 */
public class MarketTest {
	private Location location;
	private Commodity offered;
	private Commodity required;

	private Market victim;

	@Before
	public void setup() {
		location = mock(Location.class);
		offered = mock(Commodity.class);
		required = mock(Commodity.class);
		doReturn(true).when(location).checkCommodity(offered);
		doReturn(true).when(location).checkCommodity(required);

		victim = new Market() {
			@Override
			public String getId() {
				return null;
			}

			@Override
			public String getName() {
				return null;
			}

			@Override
			public Location getLocation() {
				return location;
			}

			@Override
			public Commodity getOffered() {
				return offered;
			}

			@Override
			public Commodity getRequired() {
				return required;
			}

			@Override
			public Collection<? extends ExchangeOffer> getOrders() {
				return new LinkedList<>();
			}

			@Override
			public Owner getOwner() {
				return null;
			}

			@Override
			public boolean accept(ExchangeOffer exchangeOffer) {
				return false;
			}
		};
	}

	@Test
	public void getOrders() throws Exception {
		Collection<? extends ExchangeOffer> result = victim.getOrders();
		assertEquals(0, result.size());
	}

	@Test
	public void validate() throws Exception {
		boolean result = victim.validate();
		assertEquals(true, result);
	}

	@Test
	public void validateLocation() throws Exception {
		Location location = mock(Location.class);
		doReturn(true).when(location).checkCommodity(offered);
		doReturn(true).when(location).checkCommodity(required);

		boolean result = victim.validate(location);
		assertEquals(true, result);
	}

	@Test
	public void validateExchangeOffer() throws Exception {
		ExchangeOffer exchangeOffer = mock(ExchangeOffer.class);
		doReturn(offered).when(exchangeOffer).getOffered();
		doReturn(required).when(exchangeOffer).getRequired();

		boolean result = victim.validate(exchangeOffer);
		assertEquals(true, result);
	}

	@Test
	public void isMarket() throws Exception {
		ExchangeOffer exchangeOffer = mock(ExchangeOffer.class);
		doReturn(offered).when(exchangeOffer).getOffered();
		doReturn(required).when(exchangeOffer).getRequired();

		boolean result = victim.isMarket(exchangeOffer);
		assertEquals(true, result);
	}

	@Test
	public void isCounter() throws Exception {
		ExchangeOffer exchangeOffer = mock(ExchangeOffer.class);
		doReturn(required).when(exchangeOffer).getOffered();
		doReturn(offered).when(exchangeOffer).getRequired();

		boolean result = victim.isCounter(exchangeOffer);
		assertEquals(true, result);
	}

}