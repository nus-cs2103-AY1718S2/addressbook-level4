package seedu.address.commons.events.network;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.book.Book;

/**
 * Represents the results of a search for book details using the Google Books API.
 */
public class GoogleApiBookDetailsResultEvent extends BaseEvent {

    public final ResultOutcome outcome;
    public final Book book;

    public GoogleApiBookDetailsResultEvent(ResultOutcome outcome, Book book) {
        this.outcome = outcome;
        this.book = book;
    }

    @Override
    public String toString() {
        if (outcome == ResultOutcome.FAILURE) {
            return "API failure";
        }
        return "book: " + book;
    }
}
