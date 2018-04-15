package seedu.address.model.interview.exceptions;

import seedu.address.commons.exceptions.DuplicateDataException;

/**
 * Signals that the operation will result in duplicate Interview objects.
 */
public class DuplicateInterviewException extends DuplicateDataException {
    public DuplicateInterviewException() {
        super("Operation would result in duplicate jobs");
    }
}
