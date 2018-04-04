package seedu.organizer.model.user;

import static java.util.Objects.requireNonNull;
import static seedu.organizer.commons.util.AppUtil.checkArgument;

//@@author dominickenn
/**
 * Represents a User in the organizer.
 * Guarantees: immutable;
 * username is valid as declared in {@link #isValidUsername(String)}
 * password is valid as declared in {@link #isValidPassword(String)}
 */
public class User {

    public static final String MESSAGE_USER_CONSTRAINTS = "Username and password should be alphanumeric"
                                                            + " and must not contain spaces";
    public static final String USERNAME_VALIDATION_REGEX = "\\p{Alnum}+";
    public static final String PASSWORD_VALIDATION_REGEX = "\\p{Alnum}+";

    public final String username;
    public final String password;

    /**
     * Constructs a {@code User}.
     *
     * @param username A valid username.
     * @param password A valid password.
     */
    public User(String username, String password) {
        requireNonNull(username, password);
        checkArgument(isValidUsername(username), MESSAGE_USER_CONSTRAINTS);
        checkArgument(isValidPassword(password), MESSAGE_USER_CONSTRAINTS);
        this.username = username;
        this.password = password;
    }

    /**
     * Returns true if a given string is a valid username.
     */
    public static boolean isValidUsername(String test) {
        return test.matches(USERNAME_VALIDATION_REGEX);
    }

    /**
     * Returns true if a given string is a valid password.
     */
    public static boolean isValidPassword(String test) {
        return test.matches(PASSWORD_VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof User // instanceof handles nulls
                && this.username.equals(((User) other).username)); // state check
    }

    @Override
    public int hashCode() {
        return String.join(username, password).hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return username;
    }

}
