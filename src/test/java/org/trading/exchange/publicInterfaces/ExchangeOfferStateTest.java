package org.trading.exchange.publicInterfaces;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by GArlington.
 */
public class ExchangeOfferStateTest {
	private ExchangeOffer.State victim;

	@Before
	public void setup() {
		victim = ExchangeOffer.State.OPEN;
	}

	@Test
	public void precedes() throws Exception {
		ExchangeOffer.State state = ExchangeOffer.State.PROCESSED;
		assertEquals(victim + " does not precede " + state, true, victim.precedes(state));
	}

	@Test
	public void succeeds() throws Exception {
		ExchangeOffer.State state = ExchangeOffer.State.VALIDATED;
		assertEquals(victim + " does not succeed " + state, true, victim.succeeds(state));
	}

	@Test
	public void sequence() throws Exception {
		ExchangeOffer.State precedingState = null;
		ExchangeOffer.State[] allStates = ExchangeOffer.State.values();
		for (ExchangeOffer.State state : allStates) {
			if (precedingState == null) {
				precedingState = state;
				continue;
			}
			assertEquals(precedingState + " does not precede " + state,
					true, precedingState.precedes(state));
			assertEquals(state + " does not succeed " + precedingState,
					true, state.succeeds(precedingState));
		}
	}
}