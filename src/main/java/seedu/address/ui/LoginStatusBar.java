package seedu.address.ui;

import org.controlsfx.control.StatusBar;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.layout.Region;

import seedu.address.model.login.User;

//@@author kaisertanqr
/**
 * A ui for the login status bar that is displayed at the top of the application.
 */
public class LoginStatusBar extends UiPart<Region> {

    public static final String LOGIN_STATUS_PREFIX = "Status: ";
    public static final String LOGIN_STATUS_TRUE = "Logged In as ";
    public static final String LOGIN_STATUS_FALSE = "Not Logged In";
    public static String username;

    private static final String FXML = "LoginStatusBar.fxml";

    @FXML
    private StatusBar loginStatus;

    public LoginStatusBar() {
        super(FXML);
        updateLoginStatus(false);
        registerAsAnEventHandler(this);
    }

    /**
     * Updates the UI with the login status, {@code status}.
     * @param status
     */
    public void updateLoginStatus(boolean status) {
        if (status) {
            Platform.runLater(() -> this.loginStatus.setText(LOGIN_STATUS_PREFIX + LOGIN_STATUS_TRUE));
            loginStatus.setStyle("-fx-background-color: rgb(0, 77, 26, 0.5)");
        } else {
            Platform.runLater(() -> this.loginStatus.setText(LOGIN_STATUS_PREFIX + LOGIN_STATUS_FALSE));
            loginStatus.setStyle("-fx-background-color: rgb(77, 0, 0, 0.5)");
        }
    }



}
