package seedu.address.model.task;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

//@@author JoonKai1995
/**
 * Represents a Task's priority in the todo list and calendar.
 * Guarantees: immutable; is valid as declared in {@link #isValidPriority(String)}
 */
public class Priority {


    public static final String MESSAGE_PRIORITY_CONSTRAINTS =
            "Priority value input can only be a value from 1 to 3. 1 being lowest priority and 3 being highest.";
    public static final String PRIORITY_VALIDATION_REGEX = "[1-3]{1}";
    public final String priority;
    public final int value;

    /**
     * Constructs a {@code Phone}.
     *
     * @param priorityValue A valid priority value.
     */
    public Priority(String priorityValue) {
        requireNonNull(priorityValue);
        checkArgument(isValidPriority(priorityValue), MESSAGE_PRIORITY_CONSTRAINTS);
        this.priority = priorityValue;
        this.value = Integer.parseInt(priorityValue);
    }

    /**
     * Returns true if a given string is a valid task priority.
     */
    public static boolean isValidPriority(String test) {
        return test.matches(PRIORITY_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return priority;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Priority // instanceof handles nulls
                && this.priority.equals(((Priority) other).priority)); // state check
    }

    @Override
    public int hashCode() {
        return priority.hashCode();
    }

}
