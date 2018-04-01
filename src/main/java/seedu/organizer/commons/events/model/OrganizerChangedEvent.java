package seedu.organizer.commons.events.model;

import seedu.organizer.commons.events.BaseEvent;
import seedu.organizer.model.ReadOnlyOrganizer;

/** Indicates the Organizer in the model has changed*/
public class OrganizerChangedEvent extends BaseEvent {

    public final ReadOnlyOrganizer data;

    public OrganizerChangedEvent(ReadOnlyOrganizer data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "number of tasks " + data.getCurrentUserTaskList().size()
                + ", number of tags "
                + data.getTagList().size();
    }
}
