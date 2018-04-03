package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

//@@author AzuraAiR
/**
 * Represents a selection change in the Person List Panel
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
