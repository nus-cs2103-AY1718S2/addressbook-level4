package seedu.progresschecker.model.credentials;

import static java.util.Objects.requireNonNull;

//@@author adityaa1998
/**
 * Represents the username of a user on github
 */
public class Username {

    public final String username;

    /**
     * Constructs a {@code Username}.
     *
     * @param username A valid username.
     */
    public Username(String username) {
        requireNonNull(username);
        this.username = username;
    }

    @Override
    public String toString() {
        return username;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof seedu.progresschecker.model.credentials.Username // instanceof handles nulls
                && this.username.equals(((Username) other).username)); // state check
    }

    @Override
    public int hashCode() {
        return username.hashCode();
    }

}
