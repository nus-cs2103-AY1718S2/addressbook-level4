package seedu.progresschecker.model.issues;

import static java.util.Objects.requireNonNull;
import static seedu.progresschecker.commons.util.AppUtil.checkArgument;

//@@author adityaa1998
/**
 * Represents an issue's name and description
 */
public class Title {

    public static final String MESSAGE_TITLE_CONSTRAINTS =
            "Title of the issue can be anything, but should not be blank space";

    /*
     * The first character of the title must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String TITLE_VALIDATION_REGEX = "[^\\s].*";

    public final String fullMessage;

    /**
     * Constructs a {@code Title}.
     *
     * @param title A valid description.
     */
    public Title(String title) {
        requireNonNull(title);
        checkArgument(isValidTitle(title), MESSAGE_TITLE_CONSTRAINTS);
        this.fullMessage = title;
    }

    /**
     * Returns true if a given string is a issue title.
     */
    public static boolean isValidTitle(String test) {
        return test.matches(TITLE_VALIDATION_REGEX);
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
