package org.processing;

/**
 * Created by GArlington.
 */
public interface PreProcessable extends ProcessableBase {
	default PreProcessable preProcess() {
		this.setProcessState(State.PRE_PROCESSED);
		return this;
	}
}
