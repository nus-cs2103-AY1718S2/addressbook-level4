package seedu.address.model.timetableentry.exceptions;

import seedu.address.commons.exceptions.DuplicateDataException;

/**
 * Signals that the operation will result in duplicate Person objects.
 */
public class DuplicateTimetableEntryException extends DuplicateDataException {
    public DuplicateTimetableEntryException() {
        super("Operation would result in duplicate timetable entries");
    }
}
