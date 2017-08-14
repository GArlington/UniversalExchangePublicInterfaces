package org.trading.exchange.publicInterfaces;

import java.io.Serializable;
import java.util.Collection;

/**
 * Created by GArlington.
 */
public interface Location extends Serializable, UniquelyIdentifiable {
	String getCode();

	String getName();

	String getDescription();

	Collection<? extends Commodity> getCommodities();

	Owner getOwner();

	default boolean checkCommodity(Commodity commodity) {
		return getCommodities().contains(commodity);
	}
}
