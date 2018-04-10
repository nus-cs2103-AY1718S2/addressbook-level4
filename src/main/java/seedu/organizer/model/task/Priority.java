package seedu.organizer.model.task;

import static java.util.Objects.requireNonNull;
import static seedu.organizer.commons.util.AppUtil.checkArgument;

//@@author dominickenn
/**
 * Represents a Task's priority level in the organizer.
 * Lowest Settable Priority : 0
 * Highest Settable Priority : 9
 * Guarantees: immutable; is valid as declared in {@link #isValidPriority(String)}
 */
public class Priority {

    public static final String MESSAGE_PRIORITY_CONSTRAINTS = "Priority numbers can only be 0 to 9\n"
                    + "Lowest priority : 0 | Highest priority : 9";
    public static final String PRIORITY_VALIDATION_REGEX = "\\d{1}";
    public static final String LOWEST_PRIORITY_LEVEL = "0";
    public static final String HIGHEST_SETTABLE_PRIORITY_LEVEL = "9";
    public final String value;

    /**
     * Constructs a {@code Priority}.
     *
     * @param priority A valid priority level.
     */
    public Priority(String priority) {
        requireNonNull(priority);
        checkArgument(isValidPriority(priority), MESSAGE_PRIORITY_CONSTRAINTS);
        this.value = priority;
    }

    /**
     * Returns true if a given string is a valid task priority level.
     */
    public static boolean isValidPriority(String test) {
        return test.matches(PRIORITY_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Priority // instanceof handles nulls
                && this.value.equals(((Priority) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
