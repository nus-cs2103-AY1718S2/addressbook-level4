//@@author Kyholmes
package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.ReadOnlyImdb;

/** Indicates the queue in the model has changed*/
public class QueueChangedEvent extends BaseEvent {

    public final ReadOnlyImdb data;

    public QueueChangedEvent(ReadOnlyImdb data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "number of patients in queue " + data.getUniquePatientQueue().size();
    }
}
