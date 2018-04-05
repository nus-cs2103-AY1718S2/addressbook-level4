package seedu.progresschecker.model.issues;

import static java.util.Objects.requireNonNull;
import static seedu.progresschecker.commons.util.AppUtil.checkArgument;

//@@author adityaa1998
/**
 * Represents a milestone for an issue
 */
public class Milestone {

    public static final String MESSAGE_MILESTONE_CONSTRAINTS =
            "Milestone of the issue can be anything, but should not be blank space";

    /*
     * The first character of the title must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String MILESTONE_VALIDATION_REGEX = "[^\\s].*";

    public final String fullMilestone;

    /**
     * Constructs a {@code Milestone}.
     *
     * @param milestone A valid milestone.
     */
    public Milestone(String milestone) {
        requireNonNull(milestone);
        checkArgument(isValidMilestone(milestone), MESSAGE_MILESTONE_CONSTRAINTS);
        this.fullMilestone = milestone;
    }

    /**
     * Returns true if a given string is a issue title.
     */
    public static boolean isValidMilestone(String test) {
        return test.matches(MILESTONE_VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return fullMilestone;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof seedu.progresschecker.model.issues.Milestone // instanceof handles nulls
                && this.fullMilestone.equals(((Milestone) other).fullMilestone)); // state check
    }

    @Override
    public int hashCode() {
        return fullMilestone.hashCode();
    }

}
