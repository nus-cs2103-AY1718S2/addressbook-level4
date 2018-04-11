package seedu.address.commons.events.logic;
//@@author crizyli
import seedu.address.commons.events.BaseEvent;

/**
 *  Event to request current list of employees
 */
public class GetEmployeesRequestEvent extends BaseEvent {

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
