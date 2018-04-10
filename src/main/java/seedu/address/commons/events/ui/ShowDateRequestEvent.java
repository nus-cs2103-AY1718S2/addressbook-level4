package seedu.address.commons.events.ui;

import java.time.LocalDate;

import seedu.address.commons.events.BaseEvent;

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
