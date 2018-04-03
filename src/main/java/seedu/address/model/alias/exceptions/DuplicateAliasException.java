package seedu.address.model.alias.exceptions;

import seedu.address.commons.exceptions.DuplicateDataException;

//@@author jingyinno
/**
 * Signals that the operation will result in duplicate Person objects.
 */
public class DuplicateAliasException extends DuplicateDataException {

    public static final String MESSAGE = "Operation would result in duplicate aliases";
    public DuplicateAliasException() {
        super(MESSAGE);
    }
}
