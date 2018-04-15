package seedu.progresschecker.model.issues;

import static java.util.Objects.requireNonNull;

//@@author adityaa1998
/**
 * Represents an issue's name and description
 */
public class Body {

    public static final String MESSAGE_BODY_CONSTRAINTS =
            "Issue should only contain non-null body";

    public final String fullBody;

    /**
     * Constructs a {@code Body}.
     *
     * @param body A valid issue description.
     */
    public Body(String body) {
        requireNonNull(body);
        this.fullBody = body;
    }

    /**
     * Returns true if a given string is a valid github body.
     */
    public static boolean isValidBody(String test) {
        return (test != null) ? true : false;
    }

    @Override
    public String toString() {
        return fullBody;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof seedu.progresschecker.model.issues.Body // instanceof handles nulls
                && this.fullBody.equals(((Body) other).fullBody)); // state check
    }

    @Override
    public int hashCode() {
        return fullBody.hashCode();
    }

}
