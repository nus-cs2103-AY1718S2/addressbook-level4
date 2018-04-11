package seedu.progresschecker.model.issues;

//@@author adityaa1998
/**
 * Represents an issue's name and description
 */
public class Title {

    public final String fullMessage;

    /**
     * Constructs a {@code Title}.
     *
     * @param title A valid description.
     */
    public Title(String title) {
        this.fullMessage = title;
    }

    @Override
    public String toString() {
        return fullMessage;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof seedu.progresschecker.model.issues.Title // instanceof handles nulls
                && this.fullMessage.equals(((Title) other).fullMessage)); // state check
    }

    @Override
    public int hashCode() {
        return fullMessage.hashCode();
    }

}
