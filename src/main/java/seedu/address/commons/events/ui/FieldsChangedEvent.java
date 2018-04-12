//@@author melvintzw
package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.person.Person;

/**
 * Represents a selection change in the Person List Panel
 */
public class FieldsChangedEvent extends BaseEvent {


    public final Person person;

    public FieldsChangedEvent(Person person) {
        this.person = person;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public Person getPerson() {
        return person;
    }
}
