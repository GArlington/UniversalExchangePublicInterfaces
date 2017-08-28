package org.trading.exchange.publicInterfaces;

import org.data.UniversalSerializable;
import org.security.UniquelyIdentifiable;

import java.util.Collection;

/**
 * Created by GArlington.
 */
public interface Location extends UniversalSerializable, UniquelyIdentifiable {
	String getCode();

	String getName();

	String getDescription();

	Collection<? extends Commodity> getCommodities();

	Owner getOwner();

	default boolean checkCommodity(Commodity commodity) {
		return getCommodities().contains(commodity);
	}
}
