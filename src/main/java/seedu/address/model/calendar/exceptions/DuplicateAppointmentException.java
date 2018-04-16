package seedu.address.model.calendar.exceptions;

import seedu.address.commons.exceptions.DuplicateDataException;
//@@author yuxiangSg
/**
 * Signals that the operation will result in duplicate AppointmentEntry objects.
 */

public class DuplicateAppointmentException extends DuplicateDataException {
    public DuplicateAppointmentException() {
        super("Operation would result in duplicate appointments");
    }
}
