package seedu.address.commons.events.logic;

import seedu.address.commons.events.BaseEvent;

/**
 * An listevent to inform review input is available.
 */
public class ReviewInputEvent extends BaseEvent {

    private String reviewInput;

    public ReviewInputEvent(String reviewInput) {
        this.reviewInput = reviewInput;
    }

    public String getReviewInput() {
        return reviewInput;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
