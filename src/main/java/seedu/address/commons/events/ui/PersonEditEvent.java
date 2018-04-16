package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

//@@author jstarw
/**
 * Represents a edit event of the person detail page
 */
public class PersonEditEvent extends BaseEvent {

    private final String args;

    public PersonEditEvent(String args) {
        this.args = args;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public String getArgs() {
        return args;
    }
}
