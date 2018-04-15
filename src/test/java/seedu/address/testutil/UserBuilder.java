package seedu.address.testutil;

import seedu.address.model.user.Password;
import seedu.address.model.user.User;
import seedu.address.model.user.Username;

//@@author Pearlissa
/**
 * A utility class to help with building User objects.
 */
public class UserBuilder {

    public static final String DEFAULT_USERNAME = "JOHNDOE";
    public static final String DEFAULT_PASSWORD = "12345678";

    private Username username;
    private Password password;

    public UserBuilder() {
        username = new Username(DEFAULT_USERNAME);
        password = new Password(DEFAULT_PASSWORD);
    }

    /**
     * Initializes the UserBuilder with the data of {@code userToCopy}.
     */
    public UserBuilder(User userToCopy) {
        username = userToCopy.getUsername();
        password = userToCopy.getPassword();
    }

    /**
     * Sets the {@code Userame} of the {@code User} that we are building.
     */
    public UserBuilder withUsername(String username) {
        this.username = new Username(username);
        return this;
    }

    /**
     * Sets the {@code Userame} of the {@code User} that we are building.
     */
    public UserBuilder withPassword(String password) {
        this.password = new Password(password);
        return this;
    }

    /**
     * Builds the User object
     * @return A User object
     */
    public User build() {
        return new User(username, password);
    }
}
