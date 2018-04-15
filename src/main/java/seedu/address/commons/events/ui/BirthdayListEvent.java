package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

//@@author AzuraAiR
/**
 * Represents a call for the Birthday List to be displayed
 */
public class BirthdayListEvent extends BaseEvent {

    private final String birthdayList;

    public BirthdayListEvent(String newList) {
        this.birthdayList = newList;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public String getBirthdayList() {
        return birthdayList;
    }
}
