package seedu.address.testutil;

import seedu.address.model.account.Account;
import seedu.address.model.account.Password;
import seedu.address.model.account.Username;

//@@author shadow2496
/**
 * A utility class to help with building Account objects.
 */
public class AccountBuilder {

    private static final String DEFAULT_USERNAME = "alicepauline";
    private static final String DEFAULT_PASSWORD = "alice8535";

    private Username username;
    private Password password;

    public AccountBuilder() {
        username = new Username(DEFAULT_USERNAME);
        password = new Password(DEFAULT_PASSWORD);
    }

    /**
     * Sets the {@code Username} of the {@code Account} that we are building.
     */
    public AccountBuilder withUsername(String username) {
        this.username = new Username(username);
        return this;
    }

    /**
     * Sets the {@code Password} of the {@code Account} that we are building.
     */
    public AccountBuilder withPassword(String password) {
        this.password = new Password(password);
        return this;
    }

    public Account build() {
        return new Account(username, password);
    }
}
