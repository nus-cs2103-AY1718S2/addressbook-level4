package seedu.address.login;

//@@author ngshikang
/**
 * API of the Login component
 */
public interface Login {

    /** Returns a boolean indicating if access can be allowed to user given login inputs */
    boolean checkLoginDetails(UserPass userpass) throws InvalidUsernameException, InvalidPasswordException;

    /** Returns a boolean indicating if username input is valid */
    boolean checkUsername();

    /** Returns a boolean indicating if password input corresponds to given username */
    boolean checkPassword();

    /** Stores a new username and password pair */
    void storeUserPass(UserPass userpass) throws UsernameTakenException;

    /** Initiates App for successful login **/
    void accessPermitted();

    /** Returns String that represents profile ID **/
    String getUsername();

}
