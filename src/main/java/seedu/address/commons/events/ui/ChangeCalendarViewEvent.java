package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

//@@author Robert-Peng
/**
 * Indicates a request to change calendar view
 */
public class ChangeCalendarViewEvent extends BaseEvent {

    public final Character character;

    public ChangeCalendarViewEvent(Character character) {
        this.character = character;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
