package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.ReadOnlyAddressBook;

/** Indicates the AddressBook in the model has changed
 * but status bar remains unchanged.
 * */
public class AddressBookChangedWithoutStatusBarEvent extends BaseEvent {

    public final ReadOnlyAddressBook data;

    public AddressBookChangedWithoutStatusBarEvent(ReadOnlyAddressBook data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "number of persons " + data.getPersonList().size() + ", number of tags " + data.getTagList().size();
    }
}
