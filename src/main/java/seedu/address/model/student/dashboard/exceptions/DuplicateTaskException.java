package seedu.address.model.student.dashboard.exceptions;

import seedu.address.commons.exceptions.DuplicateDataException;

//@@author yapni
/**
 * Signals that the operation will result in duplicate Task objects.
 */
public class DuplicateTaskException extends DuplicateDataException {
    public DuplicateTaskException() {
        super("Operation will result in duplicate tasks");
    }
}
