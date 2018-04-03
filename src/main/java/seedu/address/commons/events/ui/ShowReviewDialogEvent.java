package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

/**
 * An listEvent requesting to show the review dialog.
 */
public class ShowReviewDialogEvent extends BaseEvent {

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
