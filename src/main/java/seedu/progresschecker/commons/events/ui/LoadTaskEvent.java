package seedu.progresschecker.commons.events.ui;

import seedu.progresschecker.commons.events.BaseEvent;

/**
 * Represents a page change in the Browser Panel
 */
public class LoadTaskEvent extends BaseEvent {


    public final String url;

    public LoadTaskEvent(String url) {
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
