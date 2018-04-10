package seedu.address.commons.events.ui;

import java.time.Year;

import seedu.address.commons.events.BaseEvent;

//@@author trafalgarandre
/**
 * An event requesting to change view of Calendar to WeekPage.
 */
public class ShowWeekRequestEvent extends BaseEvent {
    public final Year targetYear;
    public final int targetWeek;

    public ShowWeekRequestEvent(Year year, int week) {
        this.targetYear = year;
        this.targetWeek = week;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
