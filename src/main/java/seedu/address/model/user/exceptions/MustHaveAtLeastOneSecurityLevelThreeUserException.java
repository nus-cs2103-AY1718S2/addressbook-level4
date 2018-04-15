package seedu.address.model.user.exceptions;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Signals that the username that is being added already exists.
 */
public class MustHaveAtLeastOneSecurityLevelThreeUserException extends IllegalValueException {
    public MustHaveAtLeastOneSecurityLevelThreeUserException() {
        super("You cannot delete this user as there must be at least one user with Security Level 3");
    }
}
