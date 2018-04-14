package seedu.address.model.email.exceptions;

//@@author ng95junwei

import seedu.address.commons.exceptions.DuplicateDataException;

/**
 * Signals that the operation will result in duplicate Template objects.
 */
public class DuplicateTemplateException extends DuplicateDataException {
    public DuplicateTemplateException() {
        super("Operation would result in duplicate templates");
    }
}
