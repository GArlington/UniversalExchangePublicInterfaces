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

	Collection<? extends Commodity> getCommodities();

	Owner getOwner();

	default boolean isOwned(Owner owner) {
		return getOwner().equals(owner);
	}

	boolean checkCommodity(Commodity commodity);
}
