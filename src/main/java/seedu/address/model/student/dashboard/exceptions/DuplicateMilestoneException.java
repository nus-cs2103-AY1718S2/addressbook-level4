package seedu.address.model.student.dashboard.exceptions;

import seedu.address.commons.exceptions.DuplicateDataException;

//@@author yapni
/**
 * Signals that the operation will result in duplicate Milestone objects.
 */
public class DuplicateMilestoneException extends DuplicateDataException {
    public DuplicateMilestoneException() {
        super("Operation will result in duplicate milestones");
    }
}
