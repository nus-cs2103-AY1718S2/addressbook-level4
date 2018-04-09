package seedu.address.commons.events.logic;

import seedu.address.commons.events.BaseEvent;

//@@author jlks96
/** Indicates the user is trying to make the calendar view go forward in time from the currently displaying date*/
public class CalendarGoForwardEvent extends BaseEvent {
    public CalendarGoForwardEvent() { }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
