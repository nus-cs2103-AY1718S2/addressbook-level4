package seedu.address.testutil;

import seedu.address.model.LoginManager;
import seedu.address.model.user.User;
import seedu.address.model.user.exceptions.DuplicateUserException;

/**
 * A utility class to help with building LoginManager objects.
 * Example usage: <br>
 *     {@code LoginManager lm = new LoginManagerBuilder().withUser("JOHNDOE").build();}
 */
public class LoginManagerBuilder {

    private LoginManager loginManager;

    public LoginManagerBuilder() {
        loginManager = new LoginManager();
    }

    public LoginManagerBuilder(LoginManager loginManager) {
        this.loginManager = loginManager;
    }

    /**
     * Adds a new {@code User} to the {@code LoginManager} that we are building.
     */
    public LoginManagerBuilder withUser(User user) {
        try {
            loginManager.addUser(user);
        } catch (DuplicateUserException due) {
            throw new IllegalArgumentException("user is expected to be unique.");
        }
        return this;
    }

    public LoginManager build() {
        return loginManager;
    }
}
