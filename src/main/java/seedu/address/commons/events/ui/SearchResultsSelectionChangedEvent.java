package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.ui.BookCard;

/**
 * Represents a selection change in the Search Results Panel.
 */
public class SearchResultsSelectionChangedEvent extends BaseEvent {

    private final BookCard newSelection;

    public SearchResultsSelectionChangedEvent(BookCard newSelection) {
        this.newSelection = newSelection;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public BookCard getNewSelection() {
        return newSelection;
    }
}
