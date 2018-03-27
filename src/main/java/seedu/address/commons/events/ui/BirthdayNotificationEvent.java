package seedu.address.commons.events.ui;

import javafx.collections.ObservableList;
import seedu.address.commons.events.BaseEvent;
import seedu.address.model.person.Person;

import java.time.LocalDate;

/**
 * Represents a selection change in the Person List Panel
 */
public class BirthdayNotificationEvent extends BaseEvent {

    private final String birthdayList;
    private final LocalDate currentDate;

    public BirthdayNotificationEvent(String newList, LocalDate today) {
        this.birthdayList = newList;
        this.currentDate = today;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public String getBirthdayList() {
        return birthdayList;
    }

    public LocalDate getCurrentDate() { return currentDate; }
}
