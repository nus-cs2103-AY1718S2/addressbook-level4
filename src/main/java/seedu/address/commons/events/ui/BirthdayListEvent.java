package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.person.UniquePersonList;
import seedu.address.ui.BirthdayList;
import seedu.address.ui.PersonCard;

/**
 * Represents a selection change in the Person List Panel
 */
public class BirthdayListEvent extends BaseEvent {


    private final UniquePersonList newList;

    public BirthdayListEvent(UniquePersonList newList) {
        this.newList = newList;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public UniquePersonList getNewList() {
        return newList;
    }
}
