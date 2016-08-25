package org.trading.exchange.publicInterfaces;

import org.processing.PostProcessable;
import org.processing.PreProcessable;
import org.processing.Processable;

import java.io.Serializable;

/**
 * Created by GArlington.
 */
public interface Exchangeable extends PreProcessable, Processable, PostProcessable {
	/**
	 * Get Commodity offered for exchange
	 *
	 * @return
	 */
	Commodity getOffered();

	/**
	 * Get quantity of Commodity offered for exchange
	 *
	 * @return
	 */
	long getOfferedValue();

	/**
	 * Get Required Commodity to exchange for
	 *
	 * @return
	 */
	Commodity getRequired();

	/**
	 * Get quantity of Required Commodity to exchange for
	 *
	 * @return
	 */
	long getRequiredValue();

	/**
	 * Validate Exchangeable
	 *
	 * @return
	 */
	Exchangeable validate() throws IllegalStateException;

	State getExchangeableState();

	void setExchangeableState(State state);

	enum State implements Serializable {
		INITIALISED(Processable.State.INITIALISED),
		VALIDATED(32, "Validated"),
		PRE_PROCESSED(Processable.State.PRE_PROCESSED),
		OPEN(512, "Open"),
		PROCESSED(Processable.State.PROCESSED),
		DEALT(8192, "DEALT"),
		POST_PROCESSED(Processable.State.POST_PROCESSED),
		DONE(24575, "Done"),
		FINALISED(Processable.State.FINALISED);

		final int ordinal;
		final String name;

		State(Processable.State state) {
			this(state.ordinal(), state.name());
		}

		State(int ordinal, String name) {
			this.ordinal = ordinal;
			this.name = name;
		}
	}
}
