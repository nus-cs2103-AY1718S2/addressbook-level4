//@@author jasmoon
package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

/**
 * Represents a selection change in the Panel
 */
public class PanelSelectionChangedEvent extends BaseEvent {

    private final Object newSelection;
    private final String activityType;

    public PanelSelectionChangedEvent(Object newSelection, String activityType) {
        this.newSelection = newSelection;
        this.activityType = activityType;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public Object getNewSelection() {
        return newSelection;
    }

    public String getActivityType() {
        return activityType;
    }
}
