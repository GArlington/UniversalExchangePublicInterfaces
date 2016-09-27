package org.trading.exchange.publicInterfaces;

import org.junit.Before;
import org.junit.Test;

import java.util.Collection;

/**
 * Created by GArlington.
 */
public class MarketTest {
	Market victim;

	@Before
	public void setup() {

	}

	@Test
	public void getOrders() throws Exception {
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
				return null;
			}

			@Override
			public Commodity getOffered() {
				return null;
			}

			@Override
			public Commodity getRequired() {
				return null;
			}

			@Override
			public Collection<? extends Exchangeable> getOrders() {
				return null;
			}

			@Override
			public boolean accept(Exchangeable exchangeable) {
				return false;
			}
		};
	}

	@Test
	public void validate() throws Exception {

	}

	@Test
	public void validateLocation() throws Exception {

	}

	@Test
	public void validate1() throws Exception {

	}

	@Test
	public void isMarket() throws Exception {

	}

	@Test
	public void isCounter() throws Exception {

	}

}