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
		doReturn(true).when(owner).equals(owner);
		exchangeOffer = mock(ExchangeOffer.class);
		doReturn(owner).when(exchangeOffer).getOwner();
		doReturn(true).when(exchangeOffer).isOwnedBy(owner);
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

	@Test
	public void isOwned() throws Exception {
		assertEquals(true, victim.isOwnedBy(owner));

		Owner other = mock(Owner.class);
		doReturn(false).when(exchangeOffer).isOwnedBy(other);
		assertEquals(false, victim.isOwnedBy(other));
	}

}