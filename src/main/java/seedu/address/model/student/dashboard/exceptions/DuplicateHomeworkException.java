package seedu.address.model.student.dashboard.exceptions;

import seedu.address.commons.exceptions.DuplicateDataException;

/**
 * Signals that the operation will result in duplicate Homework objects.
 */
public class DuplicateHomeworkException extends DuplicateDataException {
    public DuplicateHomeworkException() {
        super("Operation will result in duplicate homework");
    }
}
