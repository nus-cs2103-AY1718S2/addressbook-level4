package seedu.address.model.task;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Task's name in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidTaskName(String)}
 */
public class TaskName {

    public static final String MESSAGE_TASK_NAME_CONSTRAINTS =
            "Task names should only contain alphanumeric characters and spaces, and it should not be blank";

    /**
     * The first character of the task name must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String TASK_NAME_VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String value;

    /**
     * Constructs a {@code TaskName}.
     *
     * @param taskName A valid task name.
     */
    public TaskName(String taskName) {
        requireNonNull(taskName);
        checkArgument(isValidTaskName(taskName), MESSAGE_TASK_NAME_CONSTRAINTS);
        this.value = taskName;
    }

    /**
     * Returns true if a given string is a valid task name.
     */
    public static boolean isValidTaskName(String test) {
        return test.matches(TASK_NAME_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof TaskName
                && this.value.equals(((TaskName) other).value));
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
