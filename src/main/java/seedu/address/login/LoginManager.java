package seedu.address.login;

import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.LoginAccessGrantedEvent;
import seedu.address.storage.StorageManager;

import java.util.logging.Logger;

/**
 * Manages login to a specific Pigeons driver profile and customised storage.
 */
public class LoginManager extends ComponentManager implements Login {
    private static final Logger logger = LogsCenter.getLogger(LoginManager.class);
    public static final boolean CHECK_SUCCESS = true;
    private UserPassStorage userPassStorage;
    private StorageManager storage;

    public LoginManager(StorageManager storage){
        this.storage = storage;
        this.userPassStorage = storage.getUserPassStorage();
    }

    /**
     * Returns a boolean to signify login success and throws exceptions when it fails
     */
    public boolean checkLoginDetails(UserPass userpass) throws InvalidUsernameException, InvalidPasswordException {
        logger.info("----------------[USER/PASS CHECK][" + userpass.getUsername() + "]");
        if (!checkUsername(userpass)) {
            throw new InvalidUsernameException();
        } else if   (!checkPassword(userpass)) {
            throw new InvalidPasswordException();
        }
        return CHECK_SUCCESS;
    }

    /**
     * Returns a boolean to verify username is valid
     */
    public boolean checkUsername(UserPass userpass) {
        String username = userpass.getUsername();
        return userPassStorage.containsKey(username);
    }

    /**
     * Returns a boolean to verify password is valid
     */
    public boolean checkPassword(UserPass userpass) {
        String username = userpass.getUsername();
        String passwordInput = userpass.getPassword();
        String passwordExpected = userPassStorage.get(username);
        return passwordInput.equals(passwordExpected);
    }

    /**
     * Stores new username and password into storage
     */
    public void storeUserPass(UserPass userpass) throws UsernameTakenException {
        if(userPassStorage.containsKey(userpass.getUsername())) {
            throw new UsernameTakenException();
        }
        userPassStorage.put(userpass);
        storage.setUserPassStorage(userPassStorage);
    }

    /**
     * Posts new event to signify login success
     */
    public void accessPermitted(){
        EventsCenter.getInstance().post(new LoginAccessGrantedEvent());
    }

}
