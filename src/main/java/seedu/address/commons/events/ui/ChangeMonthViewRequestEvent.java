package seedu.address.commons.events.ui;

import java.time.YearMonth;

import seedu.address.commons.events.BaseEvent;

//@@author wynonaK
/**
 * Indicates a request to change to the yearmonth view of the yearmonth requested.
 */
public class ChangeMonthViewRequestEvent extends BaseEvent {

    public final YearMonth yearMonth;

    public ChangeMonthViewRequestEvent(YearMonth yearMonth) {
        this.yearMonth = yearMonth;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}

