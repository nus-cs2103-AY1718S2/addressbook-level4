package seedu.address.commons.events.network;

import seedu.address.commons.events.BaseEvent;

/**
 * Indicates a request to obtain details for a book using the Google Books API.
 */
public class GoogleApiBookDetailsRequestEvent extends BaseEvent {

    public final String bookId;

    public GoogleApiBookDetailsRequestEvent(String bookId) {
        this.bookId = bookId;
    }

    @Override
    public String toString() {
        return "book details for: " + bookId;
    }
}
