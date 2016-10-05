package org.trading.exchange.publicInterfaces;

import org.processing.PostProcessable;
import org.processing.PreProcessable;
import org.processing.Processable;

import java.io.Serializable;

/**
 * Created by GArlington.
 */
public interface ExchangeOffer extends PreProcessable, Processable, PostProcessable, Comparable {
	static ExchangeOffer validate(ExchangeOffer check) throws IllegalStateException {
		if (check.getOffered() != null && check.getRequired() != null && check.getOfferedValue() > 0L &&
				check.getRequiredValue() > 0L) {
			check.setState(State.VALIDATED);
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

	Owner getOwner();

	default boolean isOwned(Owner owner) {
		return getOwner().equals(owner);
	}

	/**
	 * Get exchange rate
	 */
	Comparable getExchangeRate();

	/**
	 * Get inverse exchange rate
	 */
	Comparable getInverseExchangeRate();

	/**
	 * Validate ExchangeOffer
	 */
	default ExchangeOffer validate() throws IllegalStateException {
		return validate(this);
	}

	/**
	 * Check if the ExchangeOffer is fully matched and is ready for further processing
	 */
	boolean isFullyMatched();

	/**
	 * Check if the ExchangeOffer passed as parameter will match (at least part fulfill) this ExchangeOffer
	 *
	 * @param exchangeOffer
	 */
	default boolean isMatching(ExchangeOffer exchangeOffer) {
		return getOffered().equals(exchangeOffer.getRequired()) && getRequired().equals(exchangeOffer.getOffered());
	}

	/**
	 * Process this ExchangeOffer and matched ExchangeOffer passed as parameter
	 *
	 * This method will change both this ExchangeOffer and ExchangeOffer passed as parameter if they match
	 *
	 * @param exchangeOffer
	 * @return processed ExchangeOffer that was passed as parameter
	 */
	ExchangeOffer match(ExchangeOffer exchangeOffer);

	Exchanged getExchanged();

	void setExchanged(Exchanged exchanged);

	State getState();

	void setState(State state);

	@Override
	default int compareTo(Object object) {
		if (this.equals(object)) return 0;
		if (object == null || getClass() != object.getClass()) return 1;
		ExchangeOffer exchangeOffer = (ExchangeOffer) object;

		int result;
		result = getOffered().compareTo(exchangeOffer.getOffered());
		if (result == 0) result = getRequired().compareTo(exchangeOffer.getRequired());
		if (result == 0) {
			@SuppressWarnings("unchecked")
			int temp = getExchangeRate().compareTo(exchangeOffer.getExchangeRate());
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

		Builder<T> setOwner(Owner owner);
	}
}
