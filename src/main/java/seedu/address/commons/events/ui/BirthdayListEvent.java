package seedu.address.commons.events.ui;

import javafx.collections.ObservableList;
import seedu.address.commons.events.BaseEvent;
import seedu.address.model.person.Person;
import seedu.address.model.person.UniquePersonList;
import seedu.address.ui.BirthdayList;
import seedu.address.ui.PersonCard;

/**
 * Represents a selection change in the Person List Panel
 */
public class BirthdayListEvent extends BaseEvent {

    private final ObservableList<Person> birthdayList;

    public BirthdayListEvent(ObservableList<Person> newList) {
        this.birthdayList = newList;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public ObservableList<Person> getBirthdayList() {
        return birthdayList;
    }
}
