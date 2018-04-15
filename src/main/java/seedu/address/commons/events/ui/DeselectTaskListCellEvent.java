package seedu.address.commons.events.ui;

import javafx.scene.control.ListView;
import seedu.address.commons.events.BaseEvent;
import seedu.address.ui.TaskCard;

/**
 * Indicates a request to deselect a ListCell of a panel.
 */
public class DeselectTaskListCellEvent extends BaseEvent {

    private final ListView<TaskCard> panel;
    private final int targetIndex;

    public DeselectTaskListCellEvent(ListView<TaskCard> panel, int targetIndex) {
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
