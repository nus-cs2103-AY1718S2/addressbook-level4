package seedu.address.commons.events.ui;

import java.time.YearMonth;

import seedu.address.commons.events.BaseEvent;

//@@author x3tsunayh

/**
 * An event requesting to jump to a specified month and year on the calendar.
 */
public class JumpToCalendarRequestEvent extends BaseEvent {

    private YearMonth yearMonth;

    public JumpToCalendarRequestEvent (YearMonth yearMonth) {
        this.yearMonth = yearMonth;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public YearMonth getYearMonth() {
        return yearMonth;
    }

}
