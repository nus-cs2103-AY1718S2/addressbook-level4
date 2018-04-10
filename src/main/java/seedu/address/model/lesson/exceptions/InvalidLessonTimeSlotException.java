package seedu.address.model.lesson.exceptions;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * @@author demitycho
 * Signals that the operation will result in duplicate Lesson objects.
 */
public class InvalidLessonTimeSlotException extends IllegalValueException {
    public InvalidLessonTimeSlotException() {
        super("Operation would result in duplicate lessons");
    }
}
