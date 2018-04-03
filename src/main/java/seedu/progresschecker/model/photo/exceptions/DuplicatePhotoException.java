package seedu.progresschecker.model.photo.exceptions;

import seedu.progresschecker.commons.exceptions.DuplicateDataException;

/**
 * Signals that the operation will result in duplicate PhotoPath objects.
 */
public class DuplicatePhotoException extends DuplicateDataException {
    public DuplicatePhotoException() {
        super("Operation would result in duplicate photos");
    }
}
