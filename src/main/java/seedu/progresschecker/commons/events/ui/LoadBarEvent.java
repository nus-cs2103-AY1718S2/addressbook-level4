package seedu.progresschecker.commons.events.ui;

import seedu.progresschecker.commons.events.BaseEvent;

//@@author EdwardKSG
/**
 * Represents a page change in the second Browser Panel which shows the progress bar.
 */
public class LoadBarEvent extends BaseEvent {


    public final String content;

    public LoadBarEvent(String content) {
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
//@@author
