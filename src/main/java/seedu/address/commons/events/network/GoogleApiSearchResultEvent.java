package seedu.address.commons.events.network;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.ReadOnlyBookShelf;

/**
 * Represents the results of a search for books using the Google Books API.
 */
public class GoogleApiSearchResultEvent extends BaseEvent {

    public final ResultOutcome outcome;
    public final ReadOnlyBookShelf bookShelf;

    public GoogleApiSearchResultEvent(ResultOutcome outcome, ReadOnlyBookShelf bookShelf) {
        this.outcome = outcome;
        this.bookShelf = bookShelf;
    }

    @Override
    public String toString() {
        if (outcome == ResultOutcome.FAILURE) {
            return "API failure";
        }
        return "number of books " + bookShelf.getBookList().size();
    }
}
