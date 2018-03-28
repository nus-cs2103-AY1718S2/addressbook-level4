package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.ReadOnlyCoinBook;

/** Indicates the CoinBook in the model has changed*/
public class CoinBookChangedEvent extends BaseEvent {

    public final ReadOnlyCoinBook data;

    public CoinBookChangedEvent(ReadOnlyCoinBook data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "number of coins " + data.getCoinList().size() + ", number of tags " + data.getTagList().size();
    }
}
