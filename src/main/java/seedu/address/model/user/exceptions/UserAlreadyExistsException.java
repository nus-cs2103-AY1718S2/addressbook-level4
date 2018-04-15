//@@author zacci
package seedu.address.model.user.exceptions;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Signals that the username that is being added already exists.
 */
public class UserAlreadyExistsException extends IllegalValueException {
    public UserAlreadyExistsException() {
        super("This username is already used");
    }
}
