package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;


/**
 * Represents an listEvent to free resources in Browser Panel
 */
public class HideBrowserPanelEvent extends BaseEvent {

    public HideBrowserPanelEvent() { }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
