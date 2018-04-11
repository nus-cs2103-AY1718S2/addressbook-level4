package seedu.progresschecker.model.exercise.exceptions;

import seedu.progresschecker.commons.exceptions.DuplicateDataException;

//@@author iNekox3
/**
 * Signals that the operation will result in duplicate Exercise objects.
 */
public class DuplicateExerciseException extends DuplicateDataException {
    public DuplicateExerciseException() {
        super("Operation would result in duplicate exercises");
    }
}
