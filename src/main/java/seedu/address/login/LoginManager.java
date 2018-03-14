package seedu.address.login;

import javafx.stage.Stage;
import seedu.address.MainApp;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.LoginAccessGrantedEvent;
import seedu.address.model.ModelManager;
import seedu.address.storage.StorageManager;
import seedu.address.ui.Ui;
import seedu.address.ui.UiManager;

import java.util.concurrent.locks.Lock;
import java.util.logging.Logger;

public class LoginManager extends ComponentManager implements Login {
    private static final Logger logger = LogsCenter.getLogger(LoginManager.class);
    public static final boolean CHECK_SUCCESS = true;
    private UserPassStorage userPassStorage;
    private StorageManager storage;

    public LoginManager(StorageManager storage){
        this.storage = storage;
        this.userPassStorage = storage.getUserPassStorage();
    }

    public boolean checkLoginDetails(UserPass userpass) throws InvalidUsernameException, InvalidPasswordException {
        logger.info("----------------[USER/PASS CHECK][" + userpass.getUsername() + "]");
        if(!checkUsername(userpass)){
            throw new InvalidUsernameException();
        }
        else if(!checkPassword(userpass)){
            throw new InvalidPasswordException();
        }
        return CHECK_SUCCESS;
    }

    public boolean checkUsername(UserPass userpass) {
        String username = userpass.getUsername();
        return userPassStorage.containsKey(username);
    }

    public boolean checkPassword(UserPass userpass) {
        String username = userpass.getUsername();
        String passwordInput = userpass.getPassword();
        String passwordExpected = userPassStorage.get(username);
        return passwordInput.equals(passwordExpected);
    }

    public void storeUserPass(UserPass userpass) throws UsernameTakenException {
        if(userPassStorage.containsKey(userpass.getUsername())){
            throw new UsernameTakenException();
        }
        userPassStorage.put(userpass);
        storage.setUserPassStorage(userPassStorage);
    }

    public void accessPermitted(){
        EventsCenter.getInstance().post(new LoginAccessGrantedEvent());
    }

}
