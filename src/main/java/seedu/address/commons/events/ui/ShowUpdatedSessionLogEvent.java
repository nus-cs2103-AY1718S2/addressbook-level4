//@@author jaronchan
package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.person.Person;

/**
 * Signals an updated session log that needs to be reloaded.
 */

public class ShowUpdatedSessionLogEvent extends BaseEvent {

    private final Person targetPerson;
    public ShowUpdatedSessionLogEvent(Person targetPerson) {
        this.targetPerson = targetPerson;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public Person getTargetPerson() {
        return targetPerson;
    }
}
