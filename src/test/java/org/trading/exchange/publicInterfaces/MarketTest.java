package org.trading.exchange.publicInterfaces;

import org.junit.Before;
import org.junit.Test;

import java.util.Collection;
import java.util.LinkedList;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

/**
 * Created by GArlington.
 */
public class MarketTest {
	private String id;
	private Location location;
	private Commodity offered;
	private Commodity required;
	private Owner owner;

	private Market victim;

	@Before
	public void setup() {
		id = UUID.randomUUID().toString();
		location = mock(Location.class);
		offered = mock(Commodity.class);
		required = mock(Commodity.class);
		owner = mock(Owner.class);
		doReturn(true).when(location).checkCommodity(offered);
		doReturn(true).when(location).checkCommodity(required);

		victim = new Market() {
			private final Collection<? extends ExchangeOffer> offers = new LinkedList<>();

			@Override
			public String getId() {
				return id;
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

			public Collection<? extends ExchangeOffer> getOffers() {
				return offers;
			}

			@Override
			public Owner getOwner() {
				return owner;
			}
/*

			@Override
			public boolean accept(ExchangeOffer exchangeOffer) {
				@SuppressWarnings("unchecked")
				Collection<ExchangeOffer> offers = ((Collection<ExchangeOffer>) getOffers());
				return offers.add(exchangeOffer);
			}
*/
		};
	}

	@Test
	public void isFair() throws Exception {
		assertEquals(true, victim.isFair());
	}

	@Test
	public void isAutoMatching() throws Exception {
		assertEquals(true, victim.isAutoMatching());
	}

	@Test
	public void equals() throws Exception {
		assertEquals(true, victim.equals(victim));

		Market test = mock(Market.class);
		assertEquals(false, victim.equals(test));
	}

	@Test
	public void accept() throws Exception {
		int count = 0;
		ExchangeOffer exchangeOffer;
		// Market offer
		exchangeOffer = createValidExchangeOfferMock(ExchangeOffer.State.OPEN, offered, 1L, required, 1L);
		assertEquals(true, victim.validate(exchangeOffer));

		assertEquals(exchangeOffer, victim.accept(exchangeOffer));
		count++;
		assertEquals(count, victim.getOffers().size());
		assertEquals(true, victim.getOffers().contains(exchangeOffer));

		// Counter offer
		exchangeOffer = createValidExchangeOfferMock(ExchangeOffer.State.OPEN, required, 1L, offered, 1L);
		assertEquals(true, victim.validate(exchangeOffer));

		assertEquals(exchangeOffer, victim.accept(exchangeOffer));
		count++;
		assertEquals(count, victim.getOffers().size());
		assertEquals(true, victim.getOffers().contains(exchangeOffer));

		// Invalid offer
		exchangeOffer = createValidExchangeOfferMock(ExchangeOffer.State.OPEN, offered, 1L, offered, 1L);
		assertEquals(false, victim.validate(exchangeOffer));

		assertEquals(null, victim.accept(exchangeOffer));
		assertEquals(count, victim.getOffers().size());
		assertEquals(false, victim.getOffers().contains(exchangeOffer));
	}

	private ExchangeOffer createValidExchangeOfferMock(ExchangeOffer.State state, Commodity offered, long offeredValue,
													   Commodity required, long requiredValue) {
		ExchangeOffer exchangeOffer = mock(ExchangeOffer.class);
		doReturn(state).when(exchangeOffer).getState();
		doReturn(exchangeOffer).when(exchangeOffer).preProcess();
		doReturn(offered).when(exchangeOffer).getOffered();
		doReturn(required).when(exchangeOffer).getRequired();
		doReturn(offeredValue).when(exchangeOffer).getOfferedValue();
		doReturn(requiredValue).when(exchangeOffer).getRequiredValue();
		return exchangeOffer;
	}

	@Test
	public void getOffers() throws Exception {
		Collection<? extends ExchangeOffer> result = victim.getOffers();
		assertEquals(0, result.size());
		ExchangeOffer exchangeOffer;
		exchangeOffer = createValidExchangeOfferMock(ExchangeOffer.State.OPEN, offered, 1L, required, 1L);
		assertEquals(exchangeOffer, victim.accept(exchangeOffer));
		result = victim.getOffers();
		assertEquals(1, result.size());
		assertEquals(true, result.contains(exchangeOffer));
	}

	@Test
	public void getOffersByState() throws Exception {
		Collection<? extends ExchangeOffer> result = victim.getOffers(ExchangeOffer.State.OPEN);
		int expectedCount = 0;
		assertEquals(expectedCount, result.size());

		ExchangeOffer exchangeOffer;

		exchangeOffer = createValidExchangeOfferMock(ExchangeOffer.State.OPEN, offered, 1L, required, 1L);
		assertEquals(exchangeOffer, victim.accept(exchangeOffer));

		result = victim.getOffers(ExchangeOffer.State.OPEN);
		assertEquals(++expectedCount, result.size());
		assertEquals(true, result.contains(exchangeOffer));

		exchangeOffer = createValidExchangeOfferMock(ExchangeOffer.State.OPEN, offered, 1L, required, 1L);
		assertEquals(exchangeOffer, victim.accept(exchangeOffer));

		result = victim.getOffers(ExchangeOffer.State.OPEN);
		assertEquals(++expectedCount, result.size());
		assertEquals(true, result.contains(exchangeOffer));

		exchangeOffer = createValidExchangeOfferMock(ExchangeOffer.State.OPEN, required, 1L, offered, 1L);
		assertEquals(exchangeOffer, victim.accept(exchangeOffer));

		result = victim.getOffers(ExchangeOffer.State.OPEN);
		assertEquals(++expectedCount, result.size());
		assertEquals(true, result.contains(exchangeOffer));
	}

	@Test
	public void validate() throws Exception {
		assertEquals(true, victim.validate());
	}

	@Test(expected = IllegalStateException.class)
	public void validateInvalidLocation() throws Exception {
		Commodity offered = mock(Commodity.class);
		Commodity required = mock(Commodity.class);
		location = mock(Location.class);
		doReturn(true).when(location).checkCommodity(offered);
		doReturn(false).when(location).checkCommodity(required);

		assertEquals(true, victim.validate());
	}

	@Test
	public void validateLocation() throws Exception {
		Location location = mock(Location.class);
		doReturn(true).when(location).checkCommodity(offered);
		doReturn(true).when(location).checkCommodity(required);

		boolean result = victim.validate(location);
		assertEquals(true, result);

		doReturn(false).when(location).checkCommodity(required);

		result = victim.validate(location);
		assertEquals(false, result);
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