package org.trading.exchange.publicInterfaces;

import org.processing.PostProcessable;
import org.processing.PreProcessable;
import org.processing.Processable;

import java.io.Serializable;

/**
 * Created by GArlington.
 */
public interface Exchangeable extends PreProcessable, Processable, PostProcessable, Comparable {
	static Exchangeable validate(Exchangeable check) throws IllegalStateException {
		if (check.getOffered() != null && check.getRequired() != null && check.getOfferedValue() > 0L &&
				check.getRequiredValue() > 0L) {
			check.setExchangeableState(State.VALIDATED);
			return check;
		} else {
			throw new IllegalStateException("All values are mandatory."
//					+ check
			);
		}
	}

	/**
	 * Get Commodity offered for exchange
	 */
	Commodity getOffered();

	/**
	 * Get quantity of Commodity offered for exchange
	 */
	long getOfferedValue();

	/**
	 * Get Required Commodity to exchange for
	 */
	Commodity getRequired();

	/**
	 * Get quantity of Required Commodity to exchange for
	 */
	long getRequiredValue();

	/**
	 * Get exchange rate
	 */
	Comparable getExchangeRate();

	/**
	 * Get inverse exchange rate
	 */
	Comparable getInverseExchangeRate();

	/**
	 * Validate Exchangeable
	 */
	default Exchangeable validate() throws IllegalStateException {
		return validate(this);
	}

	/**
	 * Check if the Exchangeable is fully matched and is ready for further processing
	 */
	boolean isFullyMatched();

	/**
	 * Check if the Exchangeable passed as parameter will match (at least part fulfill) this Exchangeable
	 *
	 * @param exchangeable
	 */
	default boolean isMatching(Exchangeable exchangeable) {
		return getOffered().equals(exchangeable.getRequired()) && getRequired().equals(exchangeable.getOffered());
	}

	/**
	 * Process this Exchangeable and matched Exchangeable passed as parameter
	 *
	 * This method will change both this Exchangeable and Exchangeable passed as parameter if they match
	 *
	 * @param exchangeable
	 * @return processed Exchangeable that was passed as parameter
	 */
	Exchangeable match(Exchangeable exchangeable);

	Exchanged getExchanged();

	void setExchanged(Exchanged exchanged);

	State getExchangeableState();

	void setExchangeableState(State state);

	@Override
	default int compareTo(Object object) {
		if (this.equals(object)) return 0;
		if (object == null || getClass() != object.getClass()) return 1;
		Exchangeable exchangeable = (Exchangeable) object;

		int result;
		result = getOffered().compareTo(exchangeable.getOffered());
		if (result == 0) result = getRequired().compareTo(exchangeable.getRequired());
		if (result == 0) {
			@SuppressWarnings("unchecked")
			int temp = getExchangeRate().compareTo(exchangeable.getExchangeRate());
			result = temp;
		}
		return result;
	}

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

		public boolean precedes(State state) {
			return ordinal < state.ordinal;
		}

		public boolean succeeds(State state) {
			return ordinal > state.ordinal;
		}
	}

	interface Builder<T> extends org.processing.Builder {
		Builder<T> setOffered(Commodity offered);

		Builder<T> setOfferedValue(long offeredValue);

		Builder<T> setRequired(Commodity required);

		Builder<T> setRequiredValue(long requiredValue);
	}
}
