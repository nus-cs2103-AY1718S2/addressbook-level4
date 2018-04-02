package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

import java.time.Year;

//@@author trafalgarandre
/**
 * An event requesting to change view of Calendar to YearPage.
 */
public class ShowYearRequestEvent extends BaseEvent {
    public final Year targetYear;

    public ShowYearRequestEvent(Year year) {
        this.targetYear = year;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
