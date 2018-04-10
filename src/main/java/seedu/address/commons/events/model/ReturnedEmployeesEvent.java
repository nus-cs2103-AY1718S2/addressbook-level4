package seedu.address.commons.events.model;
//@@author crizyli
import javafx.collections.ObservableList;
import seedu.address.commons.events.BaseEvent;
import seedu.address.model.person.Person;

/**
 * Indicates employees list returned.
 */
public class ReturnedEmployeesEvent extends BaseEvent {

    private final ObservableList<Person> employees;

    public ReturnedEmployeesEvent(ObservableList<Person> employees) {
        this.employees = employees;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public ObservableList<Person> getEmployees() {
        return employees;
    }
}
