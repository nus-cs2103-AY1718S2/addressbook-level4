package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.person.Person;

//@@author x3tsunayh

/**
 * An event requesting to google for a contact's name.
 */
public class GoogleContactNameEvent extends BaseEvent {

    private Person person;

    public GoogleContactNameEvent (Person person) {
        this.person = person;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public Person getPerson() {
        return this.person;
    }

}
