package seedu.address.commons.events.logic;

import seedu.address.commons.events.BaseEvent;

//@@author jlks96
/** Indicates the user is trying to zoom in on the calendar*/
public class ZoomInEvent extends BaseEvent {

    public ZoomInEvent() { }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
