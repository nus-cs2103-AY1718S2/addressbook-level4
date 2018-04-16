package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
//@@author yuxiangSg
/**
 * Represents a calendar GUI unfocus date request
 */
public class CalendarUnfocusEvent extends BaseEvent {
    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
