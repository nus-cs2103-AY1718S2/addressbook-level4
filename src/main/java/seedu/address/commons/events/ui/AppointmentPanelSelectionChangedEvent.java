package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.ui.AppointmentCard;

/**
 * Represents a selection change in the Appointment List Panel
 */
public class AppointmentPanelSelectionChangedEvent extends BaseEvent {

    private final AppointmentCard newSelection;

    public AppointmentPanelSelectionChangedEvent(AppointmentCard newSelection) {
        this.newSelection = newSelection;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}

