package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

import java.time.LocalDate;

//@@author trafalgarandre
/**
 * An event requesting to change view of Calendar to DatePage.
 */
public class ShowDateRequestEvent extends BaseEvent {
    public final LocalDate targetDate;

    public ShowDateRequestEvent(LocalDate date) {
        this.targetDate = date;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
