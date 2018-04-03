package seedu.address.commons.events.ui;

import java.time.LocalDate;

import seedu.address.commons.events.BaseEvent;

//@@author AzuraAiR
/**
 * Represents a call for the Birthday Notification
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

    public LocalDate getCurrentDate() {
        return currentDate;
    }

}
