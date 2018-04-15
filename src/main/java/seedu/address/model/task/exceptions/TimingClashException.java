package seedu.address.model.task.exceptions;

import static seedu.address.commons.core.Messages.MESSAGE_TASK_TIMING_CLASHES;

import seedu.address.commons.exceptions.DuplicateDataException;

//@@author ChoChihTun
/**
 * Signals that there is a clash of timing in the schedule or there is a duplicate task
 */
public class TimingClashException extends DuplicateDataException {
    public TimingClashException() {
        super(MESSAGE_TASK_TIMING_CLASHES);
    }
}
