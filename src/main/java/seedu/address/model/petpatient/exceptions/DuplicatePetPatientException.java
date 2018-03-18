package seedu.address.model.petpatient.exceptions;

import seedu.address.commons.exceptions.DuplicateDataException;

/**
 * Signals that the operation will result in duplicate PetPatient objects.
 */
public class DuplicatePetPatientException extends DuplicateDataException {
    public DuplicatePetPatientException() {
        super("Operation would result in duplicate pet patients");
    }
}
