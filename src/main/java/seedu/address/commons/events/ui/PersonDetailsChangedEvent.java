package seedu.address.commons.events.ui;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.BaseEvent;
import seedu.address.model.person.Person;

/**
 * Represents a change in the person details panel.
 *
 /** @@author Codee */
public class PersonDetailsChangedEvent extends BaseEvent {

    private final Person editedPerson;
    private final Index index;

    public PersonDetailsChangedEvent(Person editedPerson, Index index) {

        this.editedPerson = editedPerson;
        this.index = index;
    }

    public Person getPerson() {
        return this.editedPerson;
    }

    public Index getIndex() {
        return this.index;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
