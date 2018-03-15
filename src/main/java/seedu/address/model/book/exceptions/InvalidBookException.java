package seedu.address.model.book.exceptions;

/**
 * Signals that the Book object found is invalid.
 */
public class InvalidBookException extends Exception {
    public InvalidBookException(String message) {
        super(message);
    }
}
