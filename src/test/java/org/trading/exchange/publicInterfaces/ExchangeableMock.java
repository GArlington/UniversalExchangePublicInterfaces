package org.trading.exchange.publicInterfaces;

import org.processing.Processable;

/**
 * Created by GArlington.
 */
public class ExchangeableMock implements Exchangeable {
	private final Object LOCK = new Object();
	private final Commodity offered;
	private final long originalOfferedValue;
	private final Commodity required;
	private final long originalRequiredValue;
	private final Comparable exchangeRate;
	private final Comparable inverseExchangeRate;
	private long offeredValue;
	private long matchedOfferedValue;
	private long requiredValue;
	private long matchedRequiredValue;
	private Exchangeable.State exchangeableState = State.INITIALISED;
	private Processable.State processState = Processable.State.INITIALISED;
	private Exchanged exchanged;

	public ExchangeableMock(org.trading.exchange.publicInterfaces.Exchangeable exchangeable) {
		this(exchangeable.getOffered(), exchangeable.getOfferedValue(), exchangeable.getRequired(),
				exchangeable.getRequiredValue());
	}

	public ExchangeableMock(Commodity offered, long offeredValue, Commodity required, long requiredValue) {
		this.offered = offered;
		this.originalOfferedValue = this.offeredValue = offeredValue;
		this.required = required;
		this.originalRequiredValue = this.requiredValue = requiredValue;
		this.exchangeRate = offeredValue / requiredValue;
		this.inverseExchangeRate = requiredValue / offeredValue;
		initialise();
	}

	public static Builder<Exchangeable> getBuilder() {
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
	public Exchangeable match(Exchangeable exchangeable) {
		return null;
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
	public Exchangeable.State getExchangeableState() {
		return exchangeableState;
	}

	@Override
	public void setExchangeableState(Exchangeable.State state) {
		if (exchangeableState.precedes(state)) {
			this.exchangeableState = state;
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
		return "ExchangeableMock{" +
				"exchangeableState=" + exchangeableState +
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

	public static class Builder<T> implements org.trading.exchange.publicInterfaces.Exchangeable.Builder {
		private Commodity offered;
		private Commodity required;
		private long offeredValue;
		private long requiredValue;

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
		public Exchangeable build() {
			return new ExchangeableMock(offered, offeredValue, required, requiredValue);
		}
	}
}
