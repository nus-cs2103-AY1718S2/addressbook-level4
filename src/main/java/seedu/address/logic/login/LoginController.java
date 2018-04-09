//@@author cxingkai
package seedu.address.logic.login;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Controller for LoginLayout. Handles button press.
 */
public class LoginController {
    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Text messageText;

    /**
     * Authenticates input username and password when login button is clicked
     */
    @FXML
    protected void handleButtonAction(ActionEvent event) {
        String username = this.usernameField.getText();
        String password = this.passwordField.getText();
        if (username.equals("") || password.equals("")) {
            messageText.setText("Please fill in both fields.");
        } else {
            if (LoginManager.authenticate(username, password)) {
                // success
                Stage stage = (Stage) usernameField.getScene().getWindow();
                stage.close();
            } else {
                messageText.setText("Username and password do not match!");
            }
        }
    }

    /**
     * Takes in input to the various record fields when enter button is clicked in any field.
     */
    @FXML
    protected void handleKeyAction(KeyEvent event) {
        KeyCode code = event.getCode();
        if (code == KeyCode.ENTER) {
            handleEnterKey(event);
        }
    }

    /**
     * Authenticates input username and password when enter button is pressed
     */
    @FXML
    protected void handleEnterKey(KeyEvent event) {
        String username = this.usernameField.getText();
        String password = this.passwordField.getText();
        if (username.equals("") || password.equals("")) {
            messageText.setText("Please fill in both fields.");
        } else {
            if (LoginManager.authenticate(username, password)) {
                // success
                Stage stage = (Stage) usernameField.getScene().getWindow();
                stage.close();
            } else {
                messageText.setText("Username and password do not match!");
            }
        }
    }
}
