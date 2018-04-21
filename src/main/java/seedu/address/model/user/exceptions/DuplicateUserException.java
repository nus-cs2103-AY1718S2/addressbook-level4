package seedu.address.model.user.exceptions;

import seedu.address.commons.exceptions.DuplicateDataException;

//@@author Pearlissa
/**
 * Signals that the operation will result in duplicate User objects.
 */
public class DuplicateUserException extends DuplicateDataException {
    public DuplicateUserException() {
        super("Operation would result in duplicate users");
    }
}
