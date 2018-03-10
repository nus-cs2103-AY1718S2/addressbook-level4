package seedu.address.commons.events.ui;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.BaseEvent;

public class SwitchTabRequestEvent extends BaseEvent{
    
    public final int tabId;

    public SwitchTabRequestEvent(int tabId) {
        this.tabId = tabId;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
