package org.trading.exchange.publicInterfaces;

import java.io.Serializable;

/**
 * Created by GArlington.
 */
public interface Commodity extends Serializable {
	String getId();

	String getName();

	String getDescription();

	String getPriceUnit();

	String getQuantityUnit();

	long getPriceToQuantityRatio();

	/**
	 * Global commodities are currencies, they have a unique property of portability
	 * as money are not Location specific
	 */
	boolean isGlobal();
}
