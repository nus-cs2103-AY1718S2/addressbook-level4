package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

//@@author jlks96
/** Indicates that the calendar is already zoomed out to the maximum level (showing {@code YearPage})*/
public class MaxZoomOutEvent extends BaseEvent {

    public MaxZoomOutEvent() { }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
