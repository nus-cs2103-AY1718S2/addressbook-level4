//@@author LeonidAgarth
package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

/**
 * Represents a selection change in the Person List Panel
 */
public class CalendarChangedEvent extends BaseEvent {

    public CalendarChangedEvent() {
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
