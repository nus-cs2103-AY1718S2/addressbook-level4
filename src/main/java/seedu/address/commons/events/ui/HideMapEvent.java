//@@author sarahgoh97
package seedu.address.commons.events.ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.events.BaseEvent;

/**
 * Indicates an event to hide the map.
 */
public class HideMapEvent extends BaseEvent {

    public final ObservableList<String> list = FXCollections.observableArrayList();

    public HideMapEvent() {
        for (int i = 0; i < 15; i++) {
            list.add("Insufficient Access");
        }
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
