package seedu.address.login;


/**
 * API of the Login component
 */
public interface Login {

    /** Returns a boolean indicating if access can be allowed to user given login inputs */
    boolean checkLoginDetails(UserPass userpass) throws InvalidUsernameException, InvalidPasswordException;

    /** Returns a boolean indicating if username input is valid */
    boolean checkUsername(UserPass userpass);

    /** Returns a boolean indicating if password input corresponds to given username */
    boolean checkPassword(UserPass userpass);

    /** Stores a new username and password pair */
    void storeUserPass(UserPass userpass) throws UsernameTakenException;

    /** Initiates App for successful login **/
    void accessPermitted();

}
