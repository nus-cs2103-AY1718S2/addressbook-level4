package seedu.address.commons.exceptions;

/**
 * Signals that some given data does not fulfill some constraints.
 */
public class InvalidFileException extends Exception {
    /**
     * Returns InvalidFileException if {@code filePath} is not a valid XML file
     */
    public InvalidFileException() {
        super();
    }
    public InvalidFileException(String message) {
        super(message);
    }

}

