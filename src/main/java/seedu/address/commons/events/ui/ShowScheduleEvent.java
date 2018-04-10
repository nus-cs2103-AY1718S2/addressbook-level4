package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlySchedule;

/**
 * @@author demitycho
 * Indicates a request to show the milestones in a student's dashboard
 */
public class ShowScheduleEvent extends BaseEvent {

    private ReadOnlySchedule schedule;
    private ReadOnlyAddressBook addressBook;
    public ShowScheduleEvent(ReadOnlySchedule schedule, ReadOnlyAddressBook addressBook) {
        this.schedule = schedule;
        this.addressBook = addressBook;
    }

    public ReadOnlySchedule getSchedule() {
        return this.schedule;
    }

    public ReadOnlyAddressBook getAddressBook() {
        return this.addressBook;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
