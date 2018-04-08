package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

//@@author jlks96
/** Indicates that the calendar is already zoomed in to the maximum level (showing {@code DayPage})*/
public class MaxZoomInEvent extends BaseEvent {

    public MaxZoomInEvent() { }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
