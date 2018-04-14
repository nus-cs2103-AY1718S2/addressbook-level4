package seedu.progresschecker.model.credentials;

import static java.util.Objects.requireNonNull;
import static seedu.progresschecker.commons.util.AppUtil.checkArgument;

//@@author adityaa1998
/**
 * Represents the username of a user on github
 */
public class Username {
    public static final String MESSAGE_GITUSERNAME_CONSTRAINTS =
            "Username should only contain alphanumeric characters, and it should not be blank";

    /*
     * The github username can only contain alphanumeric characters,
     * with no continuous special characters.
     */
    public static final String USERNAME_VALIDATION_REGEX = "^[a-z0-9_-]{3,15}$";

    public final String username;

    /**
     * Constructs a {@code Username}.
     *
     * @param username A valid username.
     */
    public Username(String username) {
        requireNonNull(username);
        checkArgument(isValidUsername(username), MESSAGE_GITUSERNAME_CONSTRAINTS);
        this.username = username;
    }

    /**
     * Returns true if a given string is a valid github issue.
     */
    public static boolean isValidUsername(String test) {
        return test.matches(USERNAME_VALIDATION_REGEX);
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
