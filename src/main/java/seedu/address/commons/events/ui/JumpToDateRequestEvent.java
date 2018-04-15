package seedu.address.commons.events.ui;

import java.time.LocalDate;

import seedu.address.commons.events.BaseEvent;

//@@author qwlai
/**
 * Indicates a request to jump a to specified date
 */
public class JumpToDateRequestEvent extends BaseEvent {

    private LocalDate date;

    public JumpToDateRequestEvent(LocalDate date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public LocalDate getDate() {
        return date;
    }
}
