package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

//@@author ncaminh
/**
 * Show default page
 */
public class ShowDefaultPageEvent extends BaseEvent {

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
