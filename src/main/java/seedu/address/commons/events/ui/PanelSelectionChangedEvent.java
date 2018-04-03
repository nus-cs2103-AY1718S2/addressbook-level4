//@@author jasmoon
package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

/**
 * Represents a selection change in the Panel
 */
public class PanelSelectionChangedEvent extends BaseEvent {


    private final Object newSelection;

    public PanelSelectionChangedEvent(Object newSelection) {
        this.newSelection = newSelection;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public Object getNewSelection() {
        return newSelection;
    }

}
