package seedu.address.commons.events.ui;

import seedu.address.commons.core.index.Index;

public class JumpToEventListRequestEvent extends JumpToListRequestEvent {

    public JumpToEventListRequestEvent(Index targetIndex) {
        super(targetIndex);
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}