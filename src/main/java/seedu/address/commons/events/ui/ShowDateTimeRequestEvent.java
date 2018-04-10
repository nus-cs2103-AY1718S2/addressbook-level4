package seedu.address.commons.events.ui;

import java.time.LocalDateTime;

import seedu.address.commons.events.BaseEvent;

//@@author trafalgarandre
/**
 * An event requesting to display the given date and time.
 */
public class ShowDateTimeRequestEvent extends BaseEvent {
    public final LocalDateTime targetDateTime;

    public ShowDateTimeRequestEvent(LocalDateTime dateTime) {
        this.targetDateTime = dateTime;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
