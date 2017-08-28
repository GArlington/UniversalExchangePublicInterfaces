package org.trading.exchange.publicInterfaces;

import org.data.UniversalSerializable;
import org.security.UniquelyIdentifiable;

/**
 * Created by GArlington.
 */
public interface Commodity extends UniversalSerializable, Comparable<Commodity>, UniquelyIdentifiable {
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

	char getDecimalSeparator();

	@Override
	default int compareTo(Commodity object) {
		if (this.equals(object)) return 0;
		if (object == null) return 1;
		return getName().compareTo(object.getName());
	}
}
