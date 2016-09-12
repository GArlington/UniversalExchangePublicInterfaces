package org.trading.exchange.publicInterfaces;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

/**
 * Created by GArlington.
 */
public class ExchangeableTest {
	Exchangeable victim;
	Commodity offered;
	Commodity required;

	@Before
	public void setup() {
		offered = mock(Commodity.class);
		required = mock(Commodity.class);
		doReturn(0).when(offered).compareTo(offered);
		doReturn(0).when(required).compareTo(required);
		doReturn(1).when(offered).compareTo(required);
		doReturn(-1).when(required).compareTo(offered);
		victim = new ExchangeableMock(offered, 1L, required, 2L);
	}

	@Test
	public void validate() throws Exception {
		assertEquals(victim, victim.validate());
	}

	@Test
	public void validate1() throws Exception {
		assertEquals(victim, Exchangeable.validate(victim));
	}

	@Test
	public void isMatching() throws Exception {
		Exchangeable exchangeable = mock(Exchangeable.class);
		doReturn(required).when(exchangeable).getOffered();
		doReturn(offered).when(exchangeable).getRequired();
		assertEquals(true, victim.isMatching(exchangeable));
	}

	@Test
	public void compareTo() throws Exception {
		Exchangeable exchangeable =
				new ExchangeableMock(victim.getOffered(), victim.getOfferedValue(), victim.getRequired(),
						victim.getRequiredValue());
		assertEquals(0, victim.compareTo(exchangeable));
		exchangeable =
				new ExchangeableMock(victim.getOffered(), victim.getOfferedValue(), victim.getOffered(),
						victim.getRequiredValue());
		assertEquals(-1, victim.compareTo(exchangeable));
	}

}