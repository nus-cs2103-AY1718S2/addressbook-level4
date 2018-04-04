package seedu.address.commons.events.ui;

import javafx.scene.control.ListView;
import seedu.address.commons.events.BaseEvent;
import seedu.address.ui.EventCard;

/**
 * Indicates a request to jump to the list of persons
 */
public class DeselectListCellEvent extends BaseEvent {

    public DeselectListCellEvent(ListView<EventCard> panel, int targetIndex) {
        panel.getSelectionModel().clearSelection(targetIndex);
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
