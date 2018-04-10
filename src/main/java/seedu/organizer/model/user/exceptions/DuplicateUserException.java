package seedu.organizer.model.user.exceptions;

import seedu.organizer.commons.exceptions.DuplicateDataException;

//@@author dominickenn
/**
 * Signals that an operation would have violated the 'no duplicates' property of the user list.
 */
public class DuplicateUserException extends DuplicateDataException {
    public DuplicateUserException() {
        super("Operation would result in duplicate users");
    }
}
