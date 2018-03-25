package seedu.address.commons.events.storage;

import seedu.address.commons.events.BaseEvent;

public class RequiredStudentIndexChangeEvent extends BaseEvent {

    private final int newIndex;

    public RequiredStudentIndexChangeEvent(int newIndex){
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
