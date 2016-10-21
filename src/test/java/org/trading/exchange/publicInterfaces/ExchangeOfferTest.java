package org.trading.exchange.publicInterfaces;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

/**
 * Created by GArlington.
 */
public class ExchangeOfferTest {
	private ExchangeOffer victim;
	private Commodity offered;
	private Commodity required;
	private Owner owner;

	@Before
	public void setup() {
		offered = mock(Commodity.class);
		required = mock(Commodity.class);
		doReturn(0).when(offered).compareTo(offered);
		doReturn(0).when(required).compareTo(required);
		doReturn(1).when(offered).compareTo(required);
		doReturn(-1).when(required).compareTo(offered);
		owner = mock(Owner.class);
		doReturn("thisId").when(owner).getId();
		doReturn(true).when(owner).equals(owner);
		victim = ExchangeOfferMock.getBuilder().setOffered(offered).setOfferedValue(1L).setRequired(required)
				.setRequiredValue(2L).setOwner(owner).build();
	}

	@Test
	public void validate() throws Exception {
		assertEquals(victim, victim.validate());
	}

	@Test
	public void validateStatic() throws Exception {
		assertEquals(victim, ExchangeOffer.validate(victim));
	}

	@Test(expected = IllegalStateException.class)
	public void validateStaticInvalid() throws Exception {
		ExchangeOffer test = mock(ExchangeOffer.class);
		assertEquals(victim, ExchangeOffer.validate(test));
	}

	@Test
	public void isMatching() throws Exception {
		ExchangeOffer exchangeOffer = mock(ExchangeOffer.class);
		doReturn(required).when(exchangeOffer).getOffered();
		doReturn(offered).when(exchangeOffer).getRequired();
		assertEquals(true, victim.isMatching(exchangeOffer));
	}

	@Test
	public void isOwned() throws Exception {
		assertEquals(true, victim.isOwned(owner));
	}

	@Test
	public void compareTo() throws Exception {
		ExchangeOffer exchangeOffer =
				ExchangeOfferMock.getBuilder().setOffered(victim.getOffered()).setOfferedValue(victim
						.getOfferedValue())
						.setRequired(victim.getRequired()).setRequiredValue(victim.getRequiredValue()).setOwner(owner)
						.build();
		assertEquals(0, victim.compareTo(exchangeOffer));
		exchangeOffer =
				ExchangeOfferMock.getBuilder().setOffered(victim.getOffered()).setOfferedValue(victim
						.getOfferedValue())
						.setRequired(victim.getOffered()).setRequiredValue(victim.getRequiredValue()).setOwner(owner)
						.build();
		assertEquals(-1, victim.compareTo(exchangeOffer));
	}

}