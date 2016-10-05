package org.trading.exchange.publicInterfaces;

import org.processing.Processable;

/**
 * Created by GArlington.
 */
class ExchangeOfferMock implements ExchangeOffer {
	private final Object LOCK = new Object();
	private final Commodity offered;
	private final long originalOfferedValue;
	private final Commodity required;
	private final long originalRequiredValue;
	private final Comparable exchangeRate;
	private final Comparable inverseExchangeRate;
	private final Owner owner;
	private long offeredValue;
	private long matchedOfferedValue;
	private long requiredValue;
	private long matchedRequiredValue;
	private ExchangeOffer.State state = State.INITIALISED;
	private Processable.State processState = Processable.State.INITIALISED;
	private Exchanged exchanged;

	public ExchangeOfferMock(ExchangeOffer exchangeOffer) {
		this(exchangeOffer.getOffered(), exchangeOffer.getOfferedValue(), exchangeOffer.getRequired(),
				exchangeOffer.getRequiredValue(), exchangeOffer.getOwner());
	}

	ExchangeOfferMock(Commodity offered, long offeredValue, Commodity required, long requiredValue, Owner
			owner) {
		this.offered = offered;
		this.originalOfferedValue = this.offeredValue = offeredValue;
		this.required = required;
		this.originalRequiredValue = this.requiredValue = requiredValue;
		this.exchangeRate = offeredValue / requiredValue;
		this.inverseExchangeRate = requiredValue / offeredValue;
		this.owner = owner;
		initialise();
	}

	public static Builder<ExchangeOffer> getBuilder() {
		return new Builder<>();
	}

	@Override
	public Commodity getOffered() {
		return offered;
	}

	@Override
	public long getOfferedValue() {
		return offeredValue;
	}

	@Override
	public Commodity getRequired() {
		return required;
	}

	@Override
	public long getRequiredValue() {
		return requiredValue;
	}

	@Override
	public Owner getOwner() {
		return owner;
	}

	@Override
	public Comparable getExchangeRate() {
		return exchangeRate;
	}

	@Override
	public Comparable getInverseExchangeRate() {
		return inverseExchangeRate;
	}

	@Override
	public boolean isFullyMatched() {
		return false;
	}

	@Override
	public ExchangeOffer match(ExchangeOffer exchangeOffer) {
		return exchangeOffer;
	}

	@Override
	public Exchanged getExchanged() {
		return exchanged;
	}

	@Override
	public void setExchanged(Exchanged exchanged) {
		this.exchanged = exchanged;
	}

	@Override
	public ExchangeOffer.State getState() {
		return state;
	}

	@Override
	public void setState(ExchangeOffer.State state) {
		if (this.state.precedes(state)) {
			this.state = state;
		}
	}

	public long getOriginalOfferedValue() {
		return originalOfferedValue;
	}

	public long getMatchedOfferedValue() {
		return matchedOfferedValue;
	}

	public long getOriginalRequiredValue() {
		return originalRequiredValue;
	}

	public long getMatchedRequiredValue() {
		return matchedRequiredValue;
	}

	@Override
	public Processable.State getProcessState() {
		return processState;
	}

	@Override
	public void setProcessState(Processable.State state) {
		this.processState = state;
	}

	@Override
	public String toString() {
		return "ExchangeOfferMock{" +
				"state=" + state +
				", processState=" + processState +
				", offered=" + offered +
				", offeredValue=" + offeredValue +
				", exchangeRate=" + getExchangeRate() +
				", originalOfferedValue=" + originalOfferedValue +
				", matchedOfferedValue=" + matchedOfferedValue +
				", required=" + required +
				", requiredValue=" + requiredValue +
				", inverseExchangeRate=" + getInverseExchangeRate() +
				", originalRequiredValue=" + originalRequiredValue +
				", matchedRequiredValue=" + matchedRequiredValue +
				'}' + '\n';
	}

	public static class Builder<T> implements ExchangeOffer.Builder {
		private Commodity offered;
		private Commodity required;
		private long offeredValue;
		private long requiredValue;
		private Owner owner;

		@Override
		public Builder<T> setOffered(Commodity offered) {
			this.offered = offered;
			return this;
		}

		@Override
		public Builder<T> setOfferedValue(long offeredValue) {
			this.offeredValue = offeredValue;
			return this;
		}

		@Override
		public Builder<T> setRequired(Commodity required) {
			this.required = required;
			return this;
		}

		@Override
		public Builder<T> setRequiredValue(long requiredValue) {
			this.requiredValue = requiredValue;
			return this;
		}

		@Override
		public Builder<T> setOwner(Owner owner) {
			this.owner = owner;
			return this;
		}

		@Override
		public ExchangeOffer build() {
			return new ExchangeOfferMock(offered, offeredValue, required, requiredValue, owner);
		}
	}
}
