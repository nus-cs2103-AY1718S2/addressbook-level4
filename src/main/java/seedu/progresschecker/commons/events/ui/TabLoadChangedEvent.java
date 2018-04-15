package seedu.progresschecker.commons.events.ui;

import seedu.progresschecker.commons.events.BaseEvent;

//@@author iNekox3
/**
 * Represents a tab change in Main Window.
 */
public class TabLoadChangedEvent extends BaseEvent {

    public final String type;

    public TabLoadChangedEvent(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public String getTabName() {
        return type;
    }
}
