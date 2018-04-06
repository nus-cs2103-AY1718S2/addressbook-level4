//@@author ewaldhew
package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.coin.Coin;

/**
 * Indicates some coin data in the model has changed.
 */
public class CoinChangedEvent extends BaseEvent {

    public final Coin oldCoin;
    public final Coin newCoin;

    /** Pseudo-coin record that represents the change made.
     *  @see Coin#getChangeFrom(Coin)} */
    public final Coin delCoin;

    public CoinChangedEvent(Coin oldCoin, Coin newCoin) {
        this.oldCoin = oldCoin;
        this.newCoin = newCoin;
        this.delCoin = newCoin.getChangeFrom(oldCoin);
    }

    @Override
    public String toString() {
        return "coin changed ";
    }
}
