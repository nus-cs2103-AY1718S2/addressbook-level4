package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.student.Name;

//@@author yapni
/**
 * Indicates a request to show the student's name in his dashboard
 */
public class ShowStudentNameInDashboardEvent extends BaseEvent {

    private final Name name;

    public ShowStudentNameInDashboardEvent(Name name) {
        this.name = name;
    }

    public Name getName() {
        return name;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
