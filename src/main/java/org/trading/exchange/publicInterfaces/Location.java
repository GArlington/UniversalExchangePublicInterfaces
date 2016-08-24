package org.trading.exchange.publicInterfaces;

import java.io.Serializable;
import java.util.Collection;

/**
 * Created by GArlington.
 */
public interface Location extends Serializable {
	String getCode();

	String getName();

	String getDescription();

	Collection<Commodity> getCommodities();

	boolean checkCommodity(Commodity commodity);
}
