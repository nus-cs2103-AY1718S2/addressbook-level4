//@@author Jason1im
package seedu.address.model.exception;

/**
 * Signals the error cause by bad input username or password
 * which do not meet their respective contraint.
 */
public class BadDataException extends Exception {
    public BadDataException(String message) {
        super(message);
    }
}
