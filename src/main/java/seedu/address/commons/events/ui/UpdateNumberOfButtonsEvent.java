//@@author jaronchan
package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

/**
 * An event that updates the number of Scheduler Map Buttons to display.
 */
public class UpdateNumberOfButtonsEvent extends BaseEvent {

    private final int numOfInstances;

    public UpdateNumberOfButtonsEvent(int numOfInstances) {
        this.numOfInstances = numOfInstances;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public int getNumOfInstances() {
        if (numOfInstances < 1) {
            return 0;
        }
        return numOfInstances;
    }
}
