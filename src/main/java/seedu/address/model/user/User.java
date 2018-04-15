//@@author zacci
package seedu.address.model.user;

/**
 * Represents a user of the PrisonBook
 */
public class User {

    public static final String NAME_VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    private final String username;
    private final String password;
    private final int securityLevel;

    public User (String username, String password, int securityLevel) {
        this.username = username;
        this.password = password;
        this.securityLevel = securityLevel;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public int getSecurityLevel() {
        return securityLevel;
    }

    /**
     * Returns true if a given string is a valid username.
     */
    public static boolean isValidName(String test) {
        return test.matches(NAME_VALIDATION_REGEX);
    }

    /**
     * Checks if given string matches password, returns user securityLevel if password matches, else returns -1
     */
    public int checkPassword(String enteredPassword) {
        if (enteredPassword.equals(password)) {
            return securityLevel;
        } else {
            return -1;
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof User // instanceof handles nulls
                && username.equals(((User) other).username));
    }

    @Override
    public String toString() {
        return ("Username: " + username + " securityLevel: " + securityLevel);
    }

}

