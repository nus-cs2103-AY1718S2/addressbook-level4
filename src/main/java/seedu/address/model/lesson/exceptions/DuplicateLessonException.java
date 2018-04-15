package seedu.address.model.lesson.exceptions;

import seedu.address.commons.exceptions.DuplicateDataException;

//@@author demitycho
/**
 * Signals that the operation will result in duplicate Lesson objects.
 */
public class DuplicateLessonException extends DuplicateDataException {
    public DuplicateLessonException() {
        super("Operation would result in duplicate lessons");
    }
}
