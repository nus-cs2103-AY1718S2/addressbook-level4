package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

public class UpdateNumberOfButtonsEvent extends BaseEvent{

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
