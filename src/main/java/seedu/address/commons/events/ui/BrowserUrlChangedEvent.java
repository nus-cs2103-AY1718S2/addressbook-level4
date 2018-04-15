package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
//@@author tiny767
/**
 * Indicates that the browser has changed
 */
public class BrowserUrlChangedEvent extends BaseEvent {
    private String newUrl;

    public BrowserUrlChangedEvent(String newUrl) {
        this.newUrl = newUrl;
    }
    public String getNewUrl() {
        return newUrl;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
