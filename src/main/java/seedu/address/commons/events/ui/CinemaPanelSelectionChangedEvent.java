package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.ui.CinemaCard;

/**
 * Represents a selection change in the Cinema List Panel
 */
public class CinemaPanelSelectionChangedEvent extends BaseEvent {


    private final CinemaCard newSelection;

    public CinemaPanelSelectionChangedEvent(CinemaCard newSelection) {
        this.newSelection = newSelection;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public CinemaCard getNewSelection() {
        return newSelection;
    }
}
