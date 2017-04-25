package org.trading.exchange.publicInterfaces.mocks;

import org.trading.exchange.publicInterfaces.Commodity;
import org.trading.exchange.publicInterfaces.Owner;

/**
 * Created by GArlington.
 */
public class CommodityMock implements Commodity {
	private String id;
	private long priceToQuantityRatio;
	private char decimalSeparator;
	private Owner owner;

	public CommodityMock(String id, long priceToQuantityRatio, char decimalSeparator, Owner owner) {
		this.id = id;
		this.priceToQuantityRatio = priceToQuantityRatio;
		this.decimalSeparator = decimalSeparator;
		this.owner = owner;
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public String getName() {
		return null;
	}

	@Override
	public String getDescription() {
		return null;
	}

	@Override
	public String getPriceUnit() {
		return null;
	}

	@Override
	public String getQuantityUnit() {
		return null;
	}

	@Override
	public long getPriceToQuantityRatio() {
		return priceToQuantityRatio;
	}

	@Override
	public boolean isGlobal() {
		return false;
	}

	@Override
	public Owner getOwner() {
		return owner;
	}

	@Override
	public char getDecimalSeparator() {
		return decimalSeparator;
	}
}
