package org.trading.exchange.publicInterfaces;

import java.io.Serializable;
import java.util.Collection;

/**
 * Created by GArlington.
 */
public interface Exchanged extends Serializable {
	Exchangeable getExchangeable();

	Collection<? extends Exchangeable> getMatchedExchangeables();
}
