//@@author emer7
package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.person.Person;

/**
 * Represents a person edited change
 */
public class PersonEditedEvent extends BaseEvent {

    private final int index;
    private final Person newPerson;

    public PersonEditedEvent(int index, Person newPerson) {
        this.index = index;
        this.newPerson = newPerson;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public Person getNewPerson() {
        return newPerson;
    }

    public int getIndex() {
        return index;
    }
}
