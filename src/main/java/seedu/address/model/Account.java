package seedu.address.model;

/**
 * Represents a user account.
 */
public final class Account {
    private String username = "JohnDoe";
    private String password = "12345";

    /**
     * @param username should not be null
     * @param password should not be null
     */
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
        username = newUsername;
    }

    /**
     * @param newPassword should not be null
     */
    public void updatePassword(String newPassword) {
        password = newPassword;
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
