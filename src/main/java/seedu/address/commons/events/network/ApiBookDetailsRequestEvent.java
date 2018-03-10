package seedu.address.commons.events.network;

import seedu.address.commons.events.BaseEvent;

/**
 * Indicates a request to obtain details for a book using the Google Books API.
 */
public class ApiBookDetailsRequestEvent extends BaseEvent {

    public final String bookId;

    public ApiBookDetailsRequestEvent(String bookId) {
        this.bookId = bookId;
    }

    @Override
    public String toString() {
        return "book details for: " + bookId;
    }
}
