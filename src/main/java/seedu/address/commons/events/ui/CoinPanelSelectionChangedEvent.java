package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.ui.CoinCard;

/**
 * Represents a selection change in the Coin List Panel
 */
public class CoinPanelSelectionChangedEvent extends BaseEvent {


    private final CoinCard newSelection;

    public CoinPanelSelectionChangedEvent(CoinCard newSelection) {
        this.newSelection = newSelection;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public CoinCard getNewSelection() {
        return newSelection;
    }
}
