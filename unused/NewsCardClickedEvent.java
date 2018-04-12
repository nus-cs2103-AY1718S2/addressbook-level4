//@@author Eldon-Chung-unused
package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

/**
 * Indicates a mouse click on a news card
 */
public class NewsCardClickedEvent extends BaseEvent {

    public final String url;

    public NewsCardClickedEvent(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
