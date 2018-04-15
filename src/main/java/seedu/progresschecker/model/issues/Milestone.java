package seedu.progresschecker.model.issues;

//@@author adityaa1998
/**
 * Represents a milestone for an issue
 */
public class Milestone {

    public final String fullMilestone;

    /**
     * Constructs a {@code Milestone}.
     *
     * @param milestone A valid milestone.
     */
    public Milestone(String milestone) {
        //requireNonNull(milestone);
        this.fullMilestone = milestone;
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
