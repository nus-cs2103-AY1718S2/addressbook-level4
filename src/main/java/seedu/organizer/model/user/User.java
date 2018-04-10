package seedu.organizer.model.user;

import static seedu.organizer.commons.util.AppUtil.checkArgument;
import static seedu.organizer.commons.util.CollectionUtil.requireAllNonNull;

//@@author dominickenn
/**
 * Represents a User in the organizer.
 * Guarantees: immutable;
 * {@code username} is valid as declared in {@link #isValidUsername(String)}
 * {@code password} is valid as declared in {@link #isValidPassword(String)}
 */
public class User {

    public static final String MESSAGE_USERNAME_CONSTRAINTS = "Username should be alphanumeric, "
                                                            + "be at least of length 5, "
                                                            + "and must not contain spaces";
    public static final String MESSAGE_PASSWORD_CONSTRAINTS = "Password should be alphanumeric"
                                                            + ", be at least of length 5, "
                                                            + " and must not contain spaces";
    public static final String USERNAME_VALIDATION_REGEX = "\\p{Alnum}{5,}+";
    public static final String PASSWORD_VALIDATION_REGEX = "\\p{Alnum}{5,}+";

    public final String username;
    public final String password;

    /**
     * Constructs a {@code User}.
     *
     * @param username A valid username.
     * @param password A valid password.
     */
    public User(String username, String password) {
        requireAllNonNull(username, password);
        checkArgument(isValidUsername(username), MESSAGE_USERNAME_CONSTRAINTS);
        checkArgument(isValidPassword(password), MESSAGE_PASSWORD_CONSTRAINTS);
        this.username = username;
        this.password = password;
    }

    /**
     * Returns true if {@code username} is a valid username.
     */
    public static boolean isValidUsername(String username) {
        return username.matches(USERNAME_VALIDATION_REGEX);
    }

    /**
     * Returns true if {@code password} is a valid password.
     */
    public static boolean isValidPassword(String password) {
        return password.matches(PASSWORD_VALIDATION_REGEX);
    }

    /**
     * Used in login feature
     * Used to check if {@code user1}'s and {@code user2}'s usernames matches
     */
    public static boolean usernameMatches(User user1, User user2) {
        requireAllNonNull(user1, user2);
        return user1.username.equals(user2.username);
    }

    /**
     * Used in login feature
     * Used to check if {@code user1}'s and {@code user2}'s passwords matches
     */
    public static boolean passwordMatches(User user1, User user2) {
        requireAllNonNull(user1, user2);
        return user1.password.equals(user2.password);
    }

    /**
     * Used to ensure no duplicate users
     * Users with the same username are considered duplicates
     */
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
