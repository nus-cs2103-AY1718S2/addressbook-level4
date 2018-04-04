package seedu.address.commons.events.ui;

import javafx.collections.ListChangeListener.Change;
import seedu.address.commons.events.BaseEvent;
import seedu.address.model.person.Person;

//@@author Ang-YC
/**
 * Indicates a person change in address book
 */
public class PersonChangedEvent extends BaseEvent {

    private final Change<? extends Person> personChanged;

    public PersonChangedEvent(Change<? extends Person> personChanged) {
        this.personChanged = personChanged;
    }

    public Change<? extends Person> getPersonChanged() {
        return personChanged;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
