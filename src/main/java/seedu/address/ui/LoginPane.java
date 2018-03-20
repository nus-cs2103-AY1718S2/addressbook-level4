package seedu.address.ui;


import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.ExitLoginRequestEvent;
import seedu.address.login.Login;
import seedu.address.login.UserPass;

import java.util.logging.Logger;

public class LoginPane extends UiPart<Region> {

    private static final String FXML = "LoginPane.fxml";

    private final Logger logger = LogsCenter.getLogger(CommandBox.class);
    private Login login;
    private boolean isAccessPermitted = false;

    @FXML
    private TextField usernameTextField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button submitButton;

    @FXML
    private Button createButton;

    @FXML
    private Button exitButton;

    @FXML
    private Text loginStatus;

    public LoginPane(Login login) {
        super(FXML);
        this.login = login;
    }

    @FXML
    private void checkLoginDetails() {
        if(checkLoginDetails(login)) {
            login.accessPermitted();
        }
    }

    private boolean checkLoginDetails(Login login) {
        try {
            isAccessPermitted = login.checkLoginDetails(new UserPass(usernameTextField.getText(), passwordField.getText()));
        } catch(Exception e) {
            loginStatus.setText("Login Failed.");
        }
        if(isAccessPermitted) {
            loginStatus.setText("Login Successful.");
        }
        return isAccessPermitted;
    }

    @FXML
    private void createNewAccount() {
        createNewAccount(login);
    }

    private void createNewAccount(Login login) {
        try{
            login.storeUserPass(new UserPass(usernameTextField.getText(), passwordField.getText()));
        }
        catch(Exception e) {
            loginStatus.setText("Account cannot be created. Please try a different username.");
        }
    }

    @FXML
    private void closeApplication(){
        EventsCenter.getInstance().post(new ExitLoginRequestEvent());
    }

    public boolean isAccessPermitted() {
        return isAccessPermitted;
    }
}
