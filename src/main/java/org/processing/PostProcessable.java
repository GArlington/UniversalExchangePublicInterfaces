package org.processing;

/**
 * Created by GArlington.
 */
public interface PostProcessable extends ProcessableBase {
	default PostProcessable postProcess() {
		this.setProcessState(State.POST_PROCESSED);
		return this;
	}
}
