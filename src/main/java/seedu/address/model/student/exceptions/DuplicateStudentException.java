package seedu.address.model.student.exceptions;

import seedu.address.commons.exceptions.DuplicateDataException;

/**
 * Signals that the operation will result in duplicate Student objects.
 */
public class DuplicateStudentException extends DuplicateDataException {
    public DuplicateStudentException() {
        super("Operation would result in duplicate students");
    }
}
