package seedu.address.commons.events.ui;
//@@author SuxianAlicia
import java.time.LocalDate;

import seedu.address.commons.events.BaseEvent;

/**
 * Represents a request to display given {@code date} in Calendar.
 */
public class ChangeCalendarDateRequestEvent extends BaseEvent {

    private final LocalDate date;

    public ChangeCalendarDateRequestEvent(LocalDate date) {
        this.date = date;
    }

    public LocalDate getDate() {
        return date;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
