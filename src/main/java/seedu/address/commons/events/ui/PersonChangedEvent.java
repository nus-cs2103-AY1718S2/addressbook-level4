package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.person.Person;

//@@author Ang-YC
/**
 * Indicates a person change in address book
 */
public class PersonChangedEvent extends BaseEvent {

    private final Person source;
    private final Person target;

    public PersonChangedEvent(Person source, Person target) {
        this.source = source;
        this.target = target;
    }

    public Person getSource() {
        return source;
    }

    public Person getTarget() {
        return target;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
