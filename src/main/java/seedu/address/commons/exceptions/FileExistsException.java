package seedu.address.commons.exceptions;

/**
 * Signals an error when the file path specified exists.
 */
public class FileExistsException extends IllegalValueException {
    /**
     * @param message should contain a unique filename for the failed constraint(s)
     */
    public FileExistsException(String message) {
        super(message);
    }
}
