package seedu.club.commons.events.ui;

import seedu.club.commons.events.BaseEvent;

/**
 * An event requesting to view the help page.
 */
public class DecompressMembersRequestEvent extends BaseEvent {

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
