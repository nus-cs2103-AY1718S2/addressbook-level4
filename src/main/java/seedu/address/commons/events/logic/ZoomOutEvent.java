package seedu.address.commons.events.logic;

import seedu.address.commons.events.BaseEvent;

//@@author jlks96
/** Indicates the user is trying to zoom out on the calendar*/
public class ZoomOutEvent extends BaseEvent {

    public ZoomOutEvent() { }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
