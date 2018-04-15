package seedu.address.model.person.exceptions;

import seedu.address.commons.exceptions.DuplicateDataException;

/**
 * Signals that the operation will result in duplicate Nric objects.
 */
public class DuplicateNricException extends DuplicateDataException {
    public DuplicateNricException() {
        super("Operation will result in two contacts having the same nric value");
    }
}
