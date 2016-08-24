package org.processing;

/**
 * Created by GArlington.
 */
public interface Processable extends ProcessableBase {
	default Processable initialise() {
		this.setProcessState(State.INITIALISED);
		return this;
	}

	default Processable process() {
		this.setProcessState(State.PROCESSED);
		return this;
	}

	default Processable finalise() {
		this.setProcessState(State.FINALISED);
		return this;
	}
}

