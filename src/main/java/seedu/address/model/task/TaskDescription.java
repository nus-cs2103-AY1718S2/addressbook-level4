package seedu.address.model.task;

import static seedu.address.commons.util.AppUtil.checkArgument;

//@@author JoonKai1995
/**
 * Represents a short description of a todo task
 */
public class TaskDescription {

    public static final String MESSAGE_DESCRIPTION_CONSTRAINTS =
            "Task description can take any values, and it should not be blank";

    /*
     * The first character of the description must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String DESCRIPTION_VALIDATION_REGEX = "[^\\s].*";

    public final String value;

    public final String shortDesc;

    /**
     * Constructs an {@code TaskDescription}.
     *
     * @param description A valid address.
     */
    public TaskDescription(String description) {
        assert description != null : MESSAGE_DESCRIPTION_CONSTRAINTS;
        checkArgument(isValidDescription(description), MESSAGE_DESCRIPTION_CONSTRAINTS);
        this.value = description;
        if (value.length() <= 20) {
            shortDesc = value;
        } else {
            shortDesc = value.substring(0, 20) + "...";
        }
    }

    /**
     * Returns true if a given string is a valid person email.
     */
    public static boolean isValidDescription(String test) {
        return test.matches(DESCRIPTION_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TaskDescription // instanceof handles nulls
                && this.value.equals(((TaskDescription) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
