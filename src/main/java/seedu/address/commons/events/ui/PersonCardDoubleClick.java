package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.person.Person;

//@@author jstarw
/**
 * Represents a double click event in the Person Card
 */
public class PersonCardDoubleClick extends BaseEvent {

    private final Person newSelection;

    public PersonCardDoubleClick(Person newSelection) {
        this.newSelection = newSelection;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public Person getNewSelection() {
        return newSelection;
    }
}
