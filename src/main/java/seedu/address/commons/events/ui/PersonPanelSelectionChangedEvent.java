package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.ui.PersonCard;

/**
 * Represents a selection change in the Person List Panel
 */
public class PersonPanelSelectionChangedEvent extends BaseEvent {


    private final PersonCard newSelection;
    private final int oddEvenIndex;

    public PersonPanelSelectionChangedEvent(PersonCard newSelection, int oddEvenIndex) {
        this.newSelection = newSelection;
        this.oddEvenIndex =  oddEvenIndex;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public PersonCard getNewSelection() {
        return newSelection;
    }

    public int getOddEvenIndex() {
        return oddEvenIndex;
    }
}
