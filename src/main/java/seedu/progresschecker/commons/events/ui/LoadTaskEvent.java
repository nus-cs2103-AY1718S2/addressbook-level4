package seedu.progresschecker.commons.events.ui;

import seedu.progresschecker.commons.events.BaseEvent;

/**
 * Represents a page change in the Browser Panel
 */
public class LoadTaskEvent extends BaseEvent {


    public final String content;

    public LoadTaskEvent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public String getContent() {
        return content;
    }

}
