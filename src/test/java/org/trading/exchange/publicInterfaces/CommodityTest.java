package org.trading.exchange.publicInterfaces;

import org.junit.Before;
import org.junit.Test;
import org.trading.exchange.publicInterfaces.mocks.CommodityMock;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

/**
 * Created by GArlington.
 */
public class CommodityTest {
	private String greater = "2";
	private String smaller = "1";
	private Owner owner;
	private Commodity victim, test;

	@Before
	public void setup() {
		owner = mock(Owner.class);
		doReturn(true).when(owner).equals(owner);
		victim = new CommodityMock(greater, 1000, '.', owner);
		test = new CommodityMock(smaller, 1000, '.', owner);
	}

	@Test
	public void compareTo() throws Exception {
		assertEquals(0, victim.compareTo(victim));
		assertEquals(1, victim.compareTo(test));
		assertEquals(-1, test.compareTo(victim));
	}

	@Test
	public void getValue() throws Exception {
		double qty = 1.1D;
		long expected = 1100;
		assertEquals(expected, victim.getValue(qty));
	}

	@Test
	public void getValueAsString() throws Exception {
		long value = 12345L;
		String expected = "12.345";
		assertEquals(expected, victim.getValue(value));
	}

	@Test
	public void isOwned() throws Exception {
		assertEquals(true, victim.isOwned(owner));
	}
}