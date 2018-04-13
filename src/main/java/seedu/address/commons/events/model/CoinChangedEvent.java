//@@author ewaldhew
package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.coin.Coin;

/**
 * Indicates some coin data in the model has changed.
 */
public class CoinChangedEvent extends BaseEvent {

    private static final String FORMAT_STRING = "Coin changed [%1$s] -> [%2$s]";

    public final Coin data;

    public CoinChangedEvent(Coin oldCoin, Coin newCoin) {
        assert(newCoin.getPrevState().equals(oldCoin));
        this.data = newCoin;
    }

    @Override
    public String toString() {
        return String.format(FORMAT_STRING, data.getPrevState(), data);
    }
}
