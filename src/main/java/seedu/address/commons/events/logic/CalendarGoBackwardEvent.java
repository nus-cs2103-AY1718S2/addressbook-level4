package seedu.address.commons.events.logic;

import seedu.address.commons.events.BaseEvent;

//@@author jlks96
/** Indicates the user is trying to make the calendar view go backward in time from the currently displaying date*/
public class CalendarGoBackwardEvent extends BaseEvent {
    public CalendarGoBackwardEvent() { }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
