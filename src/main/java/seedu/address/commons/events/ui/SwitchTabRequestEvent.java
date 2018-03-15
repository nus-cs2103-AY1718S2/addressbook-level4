package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

/**
 * An event requesting to switch the tab of the details page.
 */
public class SwitchTabRequestEvent extends BaseEvent {

    public final int tabId;

    public SwitchTabRequestEvent(int tabId) {
        this.tabId = tabId;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
