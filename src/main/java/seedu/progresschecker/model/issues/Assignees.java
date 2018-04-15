package seedu.progresschecker.model.issues;

import static java.util.Objects.requireNonNull;
import static seedu.progresschecker.commons.util.AppUtil.checkArgument;

//@@author adityaa1998
/**
 * Represents all the assignees to an issue
 */
public class Assignees {

    public static final String MESSAGE_ASSIGNEES_CONSTRAINTS =
            "Assignees of the issue can be anything, but should not be blank space";

    /*
     * The first character of the Assignee must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String ASSIGNEE_VALIDATION_REGEX = ".*\\w.*|[$&+,:;=?@#|'<>.^*()%!-]";

    public final String fullAssignees;

    /**
     * Constructs a {@code Assignees}.
     *
     * @param assignees A valid assignees.
     */
    public Assignees(String assignees) {
        requireNonNull(assignees);
        checkArgument(isValidAssignee(assignees), MESSAGE_ASSIGNEES_CONSTRAINTS);
        this.fullAssignees = assignees;
    }

    /**
     * Returns true if a given string is a valid github issue.
     */
    public static boolean isValidAssignee(String test) {
        return test.matches(ASSIGNEE_VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return fullAssignees;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof seedu.progresschecker.model.issues.Assignees // instanceof handles nulls
                && this.fullAssignees.equals(((Assignees) other).fullAssignees)); // state check
    }

    @Override
    public int hashCode() {
        return fullAssignees.hashCode();
    }

}
