package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

//@@author tiny767
/** Indicates that the Facebook Panel should be toggled  */
public class ToggleFacebookPanelEvent extends BaseEvent {

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
