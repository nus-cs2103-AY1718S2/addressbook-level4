package seedu.address.model.patient.exceptions;

import seedu.address.commons.exceptions.DuplicateDataException;

/**
 * Signals that the operation will result in duplicate Patient objects.
 */
public class DuplicatePatientException extends DuplicateDataException {
    public DuplicatePatientException() {
        super("Operation would result in duplicate persons");
    }
}
