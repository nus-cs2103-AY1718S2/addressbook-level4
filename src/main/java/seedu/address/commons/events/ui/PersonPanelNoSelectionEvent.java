package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

/**
 * Represents no selection in the Person List Panel
 */
//@@author phmignot
public class PersonPanelNoSelectionEvent extends BaseEvent {

    public PersonPanelNoSelectionEvent() {
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
