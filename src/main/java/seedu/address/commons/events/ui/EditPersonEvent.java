package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.person.Person;

/**
 * Represents an edit person happen
 */
public class EditPersonEvent extends BaseEvent {


    public final Person person;

    public EditPersonEvent(Person personAfterEdit) {
        this.person = personAfterEdit;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
