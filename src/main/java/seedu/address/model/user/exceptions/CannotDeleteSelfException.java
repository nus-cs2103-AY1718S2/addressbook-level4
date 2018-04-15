package seedu.address.model.user.exceptions;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Signals that the username that is being added already exists.
 */
public class CannotDeleteSelfException extends IllegalValueException {

    public static final String MESSAGE = "You cannot delete yourself";

    public CannotDeleteSelfException() {
        super("You cannot delete yourself");
    }

}
