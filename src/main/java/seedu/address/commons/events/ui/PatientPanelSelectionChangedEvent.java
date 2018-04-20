package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.ui.PatientCard;

/**
 * Represents a selection change in the Patient List Panel
 */
public class PatientPanelSelectionChangedEvent extends BaseEvent {


    private final PatientCard newSelection;

    public PatientPanelSelectionChangedEvent(PatientCard newSelection) {
        this.newSelection = newSelection;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public PatientCard getNewSelection() {
        return newSelection;
    }
}
