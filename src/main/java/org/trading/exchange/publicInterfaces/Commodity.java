package org.trading.exchange.publicInterfaces;

import java.io.Serializable;

/**
 * Created by GArlington.
 */
public interface Commodity extends Serializable, Comparable {
	/**
	 * This must be a unique identifier
	 */
	String getId();

	String getName();

	String getDescription();

	String getPriceUnit();

	String getQuantityUnit();

	long getPriceToQuantityRatio();

	default long getValue(double qty) {
		return (long) (qty * getPriceToQuantityRatio());
	}

	default String getValue(long value) {
		return "" + (value / getPriceToQuantityRatio()) + getDecimalSeparator() + (value % getPriceToQuantityRatio());
	}

	/**
	 * Global commodities are currencies, they have a unique property of portability
	 * as money are not Location specific
	 */
	boolean isGlobal();

	Owner getOwner();

	default boolean isOwned(Owner owner) {
		return getOwner().equals(owner);
	}

	char getDecimalSeparator();

	@Override
	default int compareTo(Object object) {
		if (this.equals(object)) return 0;
		if (object == null || getClass() != object.getClass()) return 1;

		Commodity commodity = (Commodity) object;
		return getId().compareTo(commodity.getId());
	}
}
