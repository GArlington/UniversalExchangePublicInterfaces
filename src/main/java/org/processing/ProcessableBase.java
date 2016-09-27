package org.processing;

import java.io.Serializable;

/**
 * Created by GArlington.
 */
interface ProcessableBase extends Serializable {
	State getProcessState();

	void setProcessState(State state);

	enum State {
		INITIALISED(0, "Initialised"),
		PRE_PROCESSED(64, "PreProcessed"),
		PROCESSED(1024, "Processed"),
		POST_PROCESSED(16384, "PostProcessed"),
		FINALISED(32767, "Finalised");

		final int ordinal;
		final String name;

		State(int ordinal, String name) {
			this.ordinal = ordinal;
			this.name = name;
		}
	}
}
