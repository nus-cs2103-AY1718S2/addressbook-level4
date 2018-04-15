package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.person.Person;

//@@author wenhao53-unused
// Feature is working but was unable to solve the many tests that were failing.
/**
 * An event requesting to show the weight log of a given person
 */
public class ShowWeightLogEvent extends BaseEvent {

    public final Person person;

    public ShowWeightLogEvent(Person person) {
        this.person = person;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
