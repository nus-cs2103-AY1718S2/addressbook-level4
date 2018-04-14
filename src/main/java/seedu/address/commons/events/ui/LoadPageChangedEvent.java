//@@author ZhangYijiong
package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

/**
 * Represents a
 */
public class LoadPageChangedEvent extends BaseEvent {

    private final String url;

    public LoadPageChangedEvent(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public String getUrl() {
        return url;
    }
}

