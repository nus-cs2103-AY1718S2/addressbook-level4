package seedu.address.model.task;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

//@@author CYX28
/**
 * Represents a Task's description in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidTaskDescription(String)}
 */
public class TaskDescription {

    public static final String MESSAGE_TASK_DESCRIPTION_CONSTRAINTS =
            "Task descriptions can contain any character, but it should not be blank";

    /**
     * The first character of the task description must not be a whitespace,
     * otherwise " " ( a blank string) becomes a valid input.
     */
    public static final String TASK_DESCRIPTION_VALIDATION_REGEX = "^\\S+[\\s\\S ]*";

    public final String value;

    /**
     * Constructs a {@code TaskDescription}.
     *
     * @param taskDescription A valid task description.
     */
    public TaskDescription(String taskDescription) {
        requireNonNull(taskDescription);
        checkArgument(isValidTaskDescription(taskDescription), MESSAGE_TASK_DESCRIPTION_CONSTRAINTS);
        this.value = taskDescription;
    }

    /**
     * Returns true if a given string is a valid task description.
     */
    public static boolean isValidTaskDescription(String test) {
        return test.matches(TASK_DESCRIPTION_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof TaskDescription
                && this.value.equals(((TaskDescription) other).value));
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
