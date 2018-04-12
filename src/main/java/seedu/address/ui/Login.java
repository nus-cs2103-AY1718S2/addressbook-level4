package seedu.address.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.LoginManager;
import seedu.address.model.user.exceptions.DuplicateUserException;


/**
 * The UI component that is responsible for receiving user command inputs.
 */
public class Login extends UiPart<Region> {

    public static final String ERROR_STYLE_CLASS = "error";
    private static final String FXML = "Login.fxml";

    private final Logger logger = LogsCenter.getLogger(CommandBox.class);
    private final LoginManager login;

    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    @FXML
    private Label passwordIndicator;

    public Login(LoginManager login) {
        super(FXML);
        this.login = login;
        setPasswordIndicator("");
    }

    /**
     * Handles the Enter button pressed event.
     */
    @FXML
    private void handleCommandInputChanged() {
        try {
            login.authenticate(username.getText(), password.getText());
        } catch (DuplicateUserException e) {
            setPasswordIndicator("Invalid username or password.");
            e.printStackTrace();
        }
    }

    private void setPasswordIndicator(String text) {
        passwordIndicator.setText(text);
    }
}
