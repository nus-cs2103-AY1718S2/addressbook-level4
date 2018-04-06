package seedu.address.commons.events.ui;
//@@author crizyli
import seedu.address.commons.events.BaseEvent;

/**
 * Represents an event indicate person card changed.
 */
public class ResetPersonCardsEvent extends BaseEvent {

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
