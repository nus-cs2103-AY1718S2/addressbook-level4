package seedu.address.commons.exceptions;

/**
 * Signals that some given data does not fulfill some constraints.
 */
public class ExistingFileException extends Exception {
    /**
     * Returns ExistingFileException if {@code filePath} XML file name already exists to avoid accidental overwrites
     */
    public ExistingFileException() {
        super();
    }
    public ExistingFileException(String message) {
        super(message);
    }

}
