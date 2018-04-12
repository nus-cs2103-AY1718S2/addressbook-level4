package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

//@@author jlks96
/**
 * Indicates that the calendar is successfully zoomed in or out
 */
public class ZoomSuccessEvent extends BaseEvent {

    public ZoomSuccessEvent() {
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
