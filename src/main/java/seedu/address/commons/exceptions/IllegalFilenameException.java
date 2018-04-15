package seedu.address.commons.exceptions;

/**
 * Signals an error caused by illegal filename format.
 */
public class IllegalFilenameException extends IllegalValueException {
    /**
     * @param message should contain correct filename format for the failed constraint(s)
     */
    public IllegalFilenameException(String message) {
        super(message);
    }
}
