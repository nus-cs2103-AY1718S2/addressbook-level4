//@@author ewaldhew
package seedu.address.commons.events.ui;

import javafx.collections.ObservableList;
import seedu.address.commons.events.BaseEvent;
import seedu.address.model.rule.Rule;

/**
 * An event requesting to view the notification manager.
 */
public class ShowNotifManRequestEvent extends BaseEvent {

    public final ObservableList<Rule> data;

    public ShowNotifManRequestEvent(ObservableList<Rule> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Show notification manager: " + data.toString();
    }

}
