package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.ReadOnlySchedule;

/** Indicates the AddressBook in the model has changed*/
public class ScheduleChangedEvent extends BaseEvent {

    public final ReadOnlySchedule data;

    public ScheduleChangedEvent(ReadOnlySchedule data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "number of lessons " + data.getSchedule().size();
    }
}
