package seedu.address.model;

import seedu.address.model.exceptions.InvalidPasswordException;
import seedu.address.model.exceptions.InvalidUsernameException;

/**
 * Represents a database which stores all the registered user accounts.
 */
public class Account {
    private final String username = "JohnDoe";
    private final String password = "12345";

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    /**
     * @return true if both inputs are valid.
     * @throws InvalidUsernameException if inputUsername is not found.
     * @throws InvalidPasswordException if inputPassword is incorrect for the inputUsername.
     */
    public boolean identify(String inputUsername, String inputPassword) throws InvalidUsernameException,
            InvalidPasswordException {
        if (!this.username.equals(inputUsername)) {
            throw new InvalidUsernameException();
        } else if (!this.password.equals(inputPassword)) {
            throw new InvalidPasswordException();
        } else {
            return true;
        }
    }
}
