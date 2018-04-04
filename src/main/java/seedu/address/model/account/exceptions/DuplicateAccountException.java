package seedu.address.model.account.exceptions;

import seedu.address.commons.exceptions.DuplicateDataException;

/**
 * Signals that the operation will result in duplicate Book objects.
 */
public class DuplicateAccountException extends DuplicateDataException {
    public DuplicateAccountException() {
        super("Operation would result in duplicate books");
    }
}
