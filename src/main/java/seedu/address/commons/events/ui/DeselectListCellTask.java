package seedu.address.commons.events.ui;

import javafx.scene.control.ListView;
import seedu.address.commons.events.BaseEvent;
import seedu.address.ui.TaskCard;

/**
 * Indicates a request to jump to the list of activities
 */
public class DeselectListCellTask extends BaseEvent {

    public DeselectListCellTask(ListView<TaskCard> panel, int targetIndex) {
        panel.getSelectionModel().clearSelection(targetIndex);
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
