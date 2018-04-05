package seedu.progresschecker.model.issues;

import static java.util.Objects.requireNonNull;

//@@author adityaa1998
/**
 * Represents all the Labels of an issue
 */
public class Labels {

    public final String fullLabels;

    /**
     * Constructs a {@code Labels}.
     *
     * @param labels valid labels.
     */
    public Labels(String labels) {
        requireNonNull(labels);
        this.fullLabels = labels;
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
