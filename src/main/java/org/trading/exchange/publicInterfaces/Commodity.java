package org.trading.exchange.publicInterfaces;

import java.io.Serializable;

/**
 * Created by GArlington.
 */
public interface Commodity extends Serializable {
	String getId();

	String getName();

	String getDescription();

	long getPriceToQuantityRatio();
}
