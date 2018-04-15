package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

//@@author jasmoon
/**
 * An event requesting to view only tasks.
 */
public class ShowTaskOnlyRequestEvent extends BaseEvent {

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }


}
