package seedu.address.commons.events.storage;

import seedu.address.commons.events.BaseEvent;

/**
 * Indicates a change in the index of the student whose full info is to be displayed.
 */
public class RequiredStudentIndexChangeEvent extends BaseEvent {

    private final int newIndex;

    public RequiredStudentIndexChangeEvent(int newIndex) {
        this.newIndex = newIndex;
    }

    public int getNewIndex() {
        return newIndex;
    }

    @Override
    public String toString() {
        return "New index to be changed : " + newIndex;
    }
}
