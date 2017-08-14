package org.trading.exchange.publicInterfaces;

import org.junit.Before;
import org.junit.Test;

import java.util.Collection;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

/**
 * Created by GArlington.
 */
public class ExchangedTest {
	private String id = UUID.randomUUID().toString();
	private Owner owner;
	private Owner matchedOwner;
	private ExchangeOffer exchangeOffer;
	private Collection<ExchangeOffer> matchedExchangeOffers;

	private Exchanged victim;

	@Before
	public void setup() {
		owner = mock(Owner.class);
		exchangeOffer = mock(ExchangeOffer.class);
		doReturn(owner).when(exchangeOffer).getOwner();
		matchedExchangeOffers = mock(Collection.class);


		victim = new Exchanged() {
			@Override
			public String getId() {
				return id;
			}

			@Override
			public ExchangeOffer getExchangeOffer() {
				return exchangeOffer;
			}

			@Override
			public Collection<? extends ExchangeOffer> getMatchedExchangeOffers() {
				return matchedExchangeOffers;
			}
		};
	}

	@Test
	public void getOwner() throws Exception {
		assertEquals(owner, victim.getOwner());
	}
}