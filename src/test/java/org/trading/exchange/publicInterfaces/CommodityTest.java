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
	private String smallest = "0";
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
		double qty;
		long expected;
		qty = 1.1D;
		expected = 1100;
		assertEquals(expected, victim.getValue(qty));
		qty = 71;
		expected = 71000;
		assertEquals(expected, victim.getValue(qty));

		Commodity test2 = new CommodityMock(smallest, 100, '.', owner);
		qty = -1.2D;
		expected = -120;
		assertEquals(expected, test2.getValue(qty));
	}

	@Test
	public void getValueAsString() throws Exception {
		long value;
		String expected;
		value = 12345L;
		expected = "12.345";
		assertEquals(expected, victim.getValue(value));
		value = 1234567L;
		expected = "1234.567";
		assertEquals(expected, victim.getValue(value));

		Commodity test2 = new CommodityMock(smallest, 100, '.', owner);
		value = 12345L;
		expected = "123.45";
		assertEquals(expected, test2.getValue(value));
	}

	@Test
	public void isOwnedBy() throws Exception {
		assertEquals(true, victim.isOwnedBy(owner));

		Owner other = mock(Owner.class);
		doReturn(false).when(owner).equals(other);
		assertEquals(false, victim.isOwnedBy(other));
	}
}