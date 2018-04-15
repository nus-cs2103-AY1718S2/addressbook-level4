package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

//@@author jonleeyz

/**
 * Indicates a request to execute the home command
 */
public class HomeRequestEvent extends BaseEvent {
    public static final String MESSAGE_HOME =
            "Home view displayed. "
            + "\n\n"
            + "Utilise one of the keyboard shortcuts below to get started!"
            + "\n"
            + "Alternatively, press \"F12\" or type \"help\" to view the User Guide!";

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
//@@author
