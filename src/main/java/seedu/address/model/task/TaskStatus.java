package seedu.address.model.task;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.Arrays;
import java.util.List;

//@@author CYX28
/**
 * Represents a Task's status in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidTaskStatus(String)}
 */
public class TaskStatus {

    public static final String TASK_STATUS_DONE = "done";
    public static final String TASK_STATUS_UNDONE = "undone";

    public static final List<String> STATUS_ORDER =
            Arrays.asList(TASK_STATUS_UNDONE, TASK_STATUS_DONE);

    public static final String MESSAGE_TASK_STATUS_CONSTRAINTS =
            "Task status can only be either done or undone, and it should not be empty.";

    /**
     * The first character of the task status must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     * The valid values for task status can start with or without capital letter.
     */
    public static final String TASK_STATUS_VALIDATION_REGEX = "^(done|undone)$";

    public final String value;

    /**
     * Constructs a {@code TaskStatus}
     *
     * @param taskStatus A valid task status.
     */
    public TaskStatus(String taskStatus) {
        requireNonNull(taskStatus);
        checkArgument(isValidTaskStatus(taskStatus), MESSAGE_TASK_STATUS_CONSTRAINTS);
        this.value = taskStatus;
    }

    /**
     * Returns true if a given string is a valid task status.
     */
    public static boolean isValidTaskStatus(String test) {
        return test.matches(TASK_STATUS_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof TaskStatus
                && this.value.equals(((TaskStatus) other).value));
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
