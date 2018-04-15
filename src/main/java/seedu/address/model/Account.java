//@@author Jason1im
package seedu.address.model;

import seedu.address.model.exception.BadDataException;

/**
 * Represents a user account.
 */
public final class Account {

    public static final String MESSAGE_USERNAME_CONSTRAINTS = "Username should be alphanumeric,"
            + " 3-20 characters long and should not contain any white space.";
    public static final String MESSAGE_PASSWORD_CONSTRAINTS = "Password should be start with a "
            + "alphanumeric character and around 5-20 characters long.\n It should contain at "
            + "least 1 digit, 1 alphabet and not contain any white space.";

    public static final String USERNAME_VALIDATION_REGEX = "\\S\\w{3,20}";

    public static final String PASSWORD_VALIDATION_REGEX = "(?=.*[A-Za-z])(?=.*\\d)[\\p{Alnum}](\\S{4,20})";

    private static final String DEFAULT_USERNAME = "Admin";
    private static final String DEFAULT_PASSWORD = "ad123";

    private String username;
    private String password;

    public Account() {
        this.username = DEFAULT_USERNAME;
        this.password = DEFAULT_PASSWORD;
    }

    public Account(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    /**
     * @param newUsername should not be null
     */
    public void updateUsername(String newUsername) throws BadDataException {
        if (isValidUsername(newUsername)) {
            username = newUsername;
        } else {
            throw new BadDataException("Bad username. " + MESSAGE_USERNAME_CONSTRAINTS);
        }
    }

    /**
     * @param newPassword should not be null
     */
    public void updatePassword(String newPassword) throws BadDataException {
        if (isValidPassword(newPassword)) {
            password = newPassword;
        } else {
            throw new BadDataException("Bad password. " + MESSAGE_PASSWORD_CONSTRAINTS);
        }
    }

    public static boolean isValidUsername(String username) {
        return username.matches(USERNAME_VALIDATION_REGEX);
    }

    public static boolean isValidPassword(String password) {
        return password.matches(PASSWORD_VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof Account)) {
            return false;
        }
        Account otherAccount = (Account) other;
        return otherAccount.getUsername().equals(this.getUsername())
                && otherAccount.getPassword().equals(this.getPassword());
    }

}
