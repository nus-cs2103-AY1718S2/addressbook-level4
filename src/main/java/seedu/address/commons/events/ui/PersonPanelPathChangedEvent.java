//@@author ZhangYijiong
package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.ui.PersonCard;

/**
 * Represents a change in the browser Panel
 */
public class PersonPanelPathChangedEvent extends BaseEvent {


    private final PersonCard newSelection;

    public PersonPanelPathChangedEvent(PersonCard newSelection) {
        this.newSelection = newSelection;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public PersonCard getNewSelection() {
        return newSelection;
    }
}
