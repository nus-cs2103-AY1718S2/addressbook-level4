package seedu.address.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;

import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.ExitLoginRequestEvent;
import seedu.address.login.Login;
import seedu.address.login.UserPass;

//@@author ngshikang
/**
 * A ui for the login screen
 */
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
    private Button loginButton;

    @FXML
    private Button createButton;

    @FXML
    private Button exitButton;

    @FXML
    private Text loginStatus;

    public LoginPane(Login login) {
        super(FXML);
        this.login = login;
        usernameTextField.requestFocus();
    }

    @FXML
    private void checkLoginDetails() {
        if (checkLoginDetails(login)) {
            login.accessPermitted();
        }
    }

    /**
     * Returns a boolean for checking login details
     */
    private boolean checkLoginDetails(Login login) {
        try {
            isAccessPermitted = login.checkLoginDetails(
                    new UserPass(usernameTextField.getText(),
                            passwordField.getText()));
        } catch (Exception e) {
            loginStatus.setText("Login Failed.");
        }
        if (isAccessPermitted) {
            loginStatus.setText("Login Successful.");
        }
        return isAccessPermitted;
    }

    @FXML
    private void createNewAccount() {
        createNewAccount(login);
    }

    /**
     * Stores new profile's UserPass into UserPassStorage
     */
    private void createNewAccount(Login login) {
        try {
            login.storeUserPass(
                    new UserPass(
                            usernameTextField.getText(),
                            passwordField.getText()));
        } catch (Exception e) {
            loginStatus.setText("Username is taken.");
        }
    }

    /**
     * Handles the key press event, {@code keyEvent}.
     */
    @FXML
    private void handleKeyPress (KeyEvent keyEvent) {
        if (keyEvent.getCode().equals(KeyCode.ENTER)) {
            keyEvent.consume();
            if (loginButton.isFocused() || passwordField.isFocused()) {
                loginButton.fire();
            } else if (createButton.isFocused()) {
                createButton.fire();
            } else if (exitButton.isFocused()) {
                exitButton.fire();
            }
        }
    }

    @FXML
    private void closeApplication() {
        EventsCenter.getInstance().post(new ExitLoginRequestEvent());
    }

    public boolean isAccessPermitted() {
        return isAccessPermitted;
    }
}
