package seedu.address.commons.events.ui;

import seedu.address.commons.core.index.Index;

/**
 * Indicates a request to jump to the list of tasks
 */
public class JumpToEventListRequestEvent extends JumpToListRequestEvent {

    public JumpToEventListRequestEvent(Index targetIndex) {
        super(targetIndex);
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
