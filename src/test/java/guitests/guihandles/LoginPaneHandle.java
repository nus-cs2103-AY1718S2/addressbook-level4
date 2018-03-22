package guitests.guihandles;

import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

/**
 * Provides a handle for {@code LoginPane}.
 */
public class LoginPaneHandle extends NodeHandle<AnchorPane>  {

    public static final String LOGIN_PANE_ID = "#loginPanePlaceholder";
    private static final String PASSWORD_FIELD_ID = "#passwordField";
    private static final String USERNAME_FIELD_ID = "#usernameTextField";
    private static final String LOGIN_BUTTON_ID = "#loginButton";
    private static final String CREATE_BUTTON_ID = "#createButton";
    private static final String EXIT_BUTTON_ID = "#exitButton";

    private final PasswordField passwordField;
    private final TextField textField;
    private final Button loginButton;
    private final Button createButton;
    private final Button exitButton;

    public LoginPaneHandle(AnchorPane mainLoginNode) {
        super(mainLoginNode);

        this.passwordField = getChildNode(PASSWORD_FIELD_ID);
        this.textField = getChildNode(USERNAME_FIELD_ID);
        this.loginButton = getChildNode(LOGIN_BUTTON_ID);
        this.createButton = getChildNode(CREATE_BUTTON_ID);
        this.exitButton = getChildNode(EXIT_BUTTON_ID);

    }

    public PasswordField getPasswordField() {
        return passwordField;
    }

    public TextField getTextField() {
        return textField;
    }

    public Button getLoginButton() {
        return loginButton;
    }

    public Button getCreateButton() {
        return createButton;
    }

    public Button getExitButton() {
        return exitButton;
    }

}
