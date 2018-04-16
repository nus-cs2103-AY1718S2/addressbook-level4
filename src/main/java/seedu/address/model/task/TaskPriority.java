package seedu.address.model.task;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.Arrays;
import java.util.List;

//@@author CYX28
/**
 * Represents a Task's priority in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidTaskPriority(String)}
 */
public class TaskPriority {

    public static final String TASK_PRIORITY_HIGH = "high";
    public static final String TASK_PRIORITY_MEDIUM = "medium";
    public static final String TASK_PRIORITY_LOW = "low";

    public static final List<String> PRIORITY_ORDER =
            Arrays.asList(TASK_PRIORITY_HIGH, TASK_PRIORITY_MEDIUM, TASK_PRIORITY_LOW);

    public static final String MESSAGE_TASK_PRIORITY_CONSTRAINTS =
            "Task priority can only be either high, medium or low, and it should not be empty";

    /**
     * The first character of the task priority must not be a whitespace,
     * otherwise " " ( a blank string) becomes a valid input.
     * The valid values for task priority can start with or without capital letter.
     */
    public static final String TASK_PRIORITY_VALIDATION_REGEX = "^(high|medium|low)$";

    public final String value;

    /**
     * Constructs a {@code TaskPriority}
     *
     * @param taskPriority A valid task priority.
     */
    public TaskPriority(String taskPriority) {
        requireNonNull(taskPriority);
        checkArgument(isValidTaskPriority(taskPriority), MESSAGE_TASK_PRIORITY_CONSTRAINTS);
        this.value = taskPriority;
    }

    /**
     * Returns true if a given string is a valid task priority.
     */
    public static boolean isValidTaskPriority(String test) {
        return test.matches(TASK_PRIORITY_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof TaskPriority
                && this.value.equals(((TaskPriority) other).value));
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
