package seedu.address.commons.events.ui;

import javafx.scene.control.ListView;
import seedu.address.commons.events.BaseEvent;
import seedu.address.ui.EventCard;

/**
 * Indicates a request to deselect a ListCell of a panel.
 */
public class DeselectEventListCellEvent extends BaseEvent {

    private final ListView<EventCard> panel;
    private final int targetIndex;

    public DeselectEventListCellEvent(ListView<EventCard> panel, int targetIndex) {
        this.panel = panel;
        this.targetIndex = targetIndex;
    }

    public ListView<?> getPanel()   {
        return panel;
    }

    public int getTargetIndex() {
        return targetIndex;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
