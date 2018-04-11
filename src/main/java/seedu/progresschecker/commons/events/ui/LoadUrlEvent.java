package seedu.progresschecker.commons.events.ui;

import seedu.progresschecker.commons.events.BaseEvent;

//@@author EdwardKSG
/**
 * Represents a page change in the Browser Panel
 */
public class LoadUrlEvent extends BaseEvent {


    public final String url;

    public LoadUrlEvent(String url) {
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
//@@author
