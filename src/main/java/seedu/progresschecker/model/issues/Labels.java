package seedu.progresschecker.model.issues;

import static java.util.Objects.requireNonNull;
import static seedu.progresschecker.commons.util.AppUtil.checkArgument;

//@@author adityaa1998
/**
 * Represents all the Labels of an issue
 */
public class Labels {

    public static final String MESSAGE_LABEL_CONSTRAINTS =
            "Labels should only contain alphanumeric characters and spaces, and it should not be blank";

    /*
     * The first character of the label must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String LABEL_VALIDATION_REGEX = ".*\\w.*|[$&+,:;=?@#|'<>.^*()%!-]";

    public final String fullLabels;

    /**
     * Constructs a {@code Labels}.
     *
     * @param labels valid labels.
     */
    public Labels(String labels) {
        requireNonNull(labels);
        checkArgument(isValidLabel(labels), MESSAGE_LABEL_CONSTRAINTS);
        this.fullLabels = labels;
    }

    /**
     * Returns true if a given string is a valid github label.
     */
    public static boolean isValidLabel(String test) {
        return test.matches(LABEL_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return fullLabels;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof seedu.progresschecker.model.issues.Labels // instanceof handles nulls
                && this.fullLabels.equals(((Labels) other).fullLabels)); // state check
    }

    @Override
    public int hashCode() {
        return fullLabels.hashCode();
    }

}
