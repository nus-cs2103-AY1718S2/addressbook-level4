//@@author Jason1im
package seedu.address.model;

/**
 * Represents a user account.
 */
public final class Account {

    public static final String MESSAGE_USERNAME_CONSTRAINTS = "Username should be alphanumeric"
            + " and it should not be bank.";
    public static final String MESSAGE_PASSWORD_CONSTRAINTS = "Password should be alphanumeric"
            + " and it should not be bank.";

    public static final String USERNAME_VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public static final String PASSWORD_VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

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
    public void updateUsername(String newUsername) {
        if (isValidUsername(newUsername)) {
            username = newUsername;
        } else {

        }
    }

    /**
     * @param newPassword should not be null
     */
    public void updatePassword(String newPassword) {
        password = newPassword;
    }

    public void resetPassword() {
        password = DEFAULT_PASSWORD;
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
