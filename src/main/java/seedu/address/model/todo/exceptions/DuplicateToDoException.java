package seedu.address.model.todo.exceptions;

import seedu.address.commons.exceptions.DuplicateDataException;

/**
 * Signals that the operation will result in duplicate ToDo objects.
 */
public class DuplicateToDoException extends DuplicateDataException {
    public DuplicateToDoException() {
        super("Operation would result in duplicate to-dos");
    }
}
