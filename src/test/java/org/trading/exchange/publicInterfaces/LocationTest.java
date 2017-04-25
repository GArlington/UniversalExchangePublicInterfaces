package org.trading.exchange.publicInterfaces;

import org.junit.Before;
import org.junit.Test;

import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

/**
 * Created by GArlington.
 */
public class LocationTest {
	private Location victim;

	private String code = "code";
	private String name = "name";
	private String description = "description";
	private Collection<? extends Commodity> commodities;
	private Owner owner;

	@Before
	public void setup() {
		commodities = mock(Collection.class);
		owner = mock(Owner.class);
		doReturn(true).when(owner).equals(owner);

		victim = new Location() {
			@Override
			public String getCode() {
				return code;
			}

			@Override
			public String getName() {
				return name;
			}

			@Override
			public String getDescription() {
				return description;
			}

			@Override
			public Collection<? extends Commodity> getCommodities() {
				return commodities;
			}

			@Override
			public Owner getOwner() {
				return owner;
			}
		};
	}

	@Test
	public void getCode() throws Exception {
		assertEquals(code, victim.getCode());
	}

	@Test
	public void getName() throws Exception {
		assertEquals(name, victim.getName());
	}

	@Test
	public void getDescription() throws Exception {
		assertEquals(description, victim.getDescription());
	}

	@Test
	public void getCommodities() throws Exception {
		assertEquals(commodities, victim.getCommodities());
	}

	@Test
	public void getOwner() throws Exception {
		assertEquals(owner, victim.getOwner());
	}

	@Test
	public void isOwned() throws Exception {
		assertEquals(true, victim.isOwned(owner));
	}

	@Test
	public void checkCommodity() throws Exception {
		Commodity commodity = mock(Commodity.class);
		doReturn(true).when(commodities).contains(commodity);
		assertEquals(true, victim.checkCommodity(commodity));
	}

}