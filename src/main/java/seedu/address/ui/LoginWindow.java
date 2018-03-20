package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;
import seedu.address.login.Login;

import java.util.logging.Logger;

/**
 * The Login Window. Provides the layout for user login before accessing the main program.
 */
public class LoginWindow extends UiPart<Stage> {

    private static final String FXML = "LoginWindow.fxml";

    private final Logger logger = LogsCenter.getLogger(this.getClass());

    private Stage primaryStage;
    private Login login;
    private LoginPane loginPane;

    @FXML
    private AnchorPane loginPanePlaceholder;

    void fillPane() {
        loginPane = new LoginPane(login);
        loginPanePlaceholder.getChildren().add(loginPane.getRoot());
    }

    boolean checkAccess() {
        return loginPane.isAccessPermitted();
    }

    public LoginWindow(Stage primaryStage, Login login) {
        super(FXML, primaryStage);

        // Set dependencies
        this.primaryStage = primaryStage;
        this.login = login;

        registerAsAnEventHandler(this);
    }

    void show() {
        primaryStage.show();
    }

    void hide(){
        primaryStage.hide();
    }

}
