package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlySchedule;

/** Indicates the AddressBook in the model has changed*/
public class ScheduleChangedEvent extends BaseEvent {

    private final ReadOnlySchedule schedule;
    private final ReadOnlyAddressBook addressBook;

    public ScheduleChangedEvent(ReadOnlySchedule schedule, ReadOnlyAddressBook addressBook) {
        this.schedule = schedule;
        this.addressBook = addressBook;
    }

    @Override
    public String toString() {
        return "number of lessons " + schedule.getSchedule().size();
    }

    public final ReadOnlySchedule getLessons() {
        return this.schedule;
    }

    public final ReadOnlyAddressBook getAddressBook() {
        return this.addressBook;
    }
}
