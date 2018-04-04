//@@author IzHoBX
package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

/**
 * Indicates a new suggestion is available.
 */
public class ShowSuggestionEvent extends BaseEvent {
    private String suggestion;

    public ShowSuggestionEvent(String suggestion) {
        this.suggestion = suggestion;
    }

    public String getSuggestion() {
        return suggestion;
    }

    @Override
    public String toString() {
        return null;
    }
}
