package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.ReadOnlyAddressBook;

/** Indicates the AddressBook in the model has changed*/
public class AddressBookChangedEvent extends BaseEvent {

    public final ReadOnlyAddressBook data;

    public AddressBookChangedEvent(ReadOnlyAddressBook data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "number of students " + data.getStudentList().size()
                + ", number of tutors " + data.getTutorList().size()
                + ", number of tags " + data.getTagList().size()
                + ", number of closed student " + data.getClosedStudentList().size()
                + ", number of closed tutor " + data.getClosedTutorList().size();
    }
}
